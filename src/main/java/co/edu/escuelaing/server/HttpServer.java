package co.edu.escuelaing.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import co.edu.escuelaing.annotations.RequestParam;

/**
 * Servidor HTTP minimalista que despacha solicitudes GET
 * hacia los controladores registrados en el framework
 * y sirve archivos estáticos desde el classpath (/static/).
 */
public class HttpServer {

    private final int puerto;
    private final Map<String, Method> rutasRegistradas = new HashMap<>();
    private final Map<String, Object> instanciasControlador = new HashMap<>();

    public HttpServer(int puerto) {
        this.puerto = puerto;
    }

    public void agregarRuta(String ruta, Method metodo, Object instancia) {
        rutasRegistradas.put(ruta, metodo);
        instanciasControlador.put(ruta, instancia);
    }

    public void iniciar() throws IOException {
        ServerSocket socketServidor = new ServerSocket(puerto);
        System.out.println("Servidor escuchando en http://localhost:" + puerto);

        while (true) {
            Socket conexion = socketServidor.accept();
            procesarSolicitud(conexion);
        }
    }

    private void procesarSolicitud(Socket conexion) throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        OutputStream salida   = conexion.getOutputStream();

        String lineaSolicitud = lector.readLine();
        if (lineaSolicitud == null || lineaSolicitud.isEmpty()) {
            conexion.close();
            return;
        }

        String[] tokens      = lineaSolicitud.split(" ");
        String rutaCompleta  = tokens[1];
        String ruta          = rutaCompleta.contains("?") ? rutaCompleta.split("\\?")[0] : rutaCompleta;
        String query         = rutaCompleta.contains("?") ? rutaCompleta.split("\\?")[1] : "";

        if (ruta.startsWith("/static/")) {
            servirArchivoEstatico(ruta, salida);
        } else if (rutasRegistradas.containsKey(ruta)) {
            Method metodo    = rutasRegistradas.get(ruta);
            Object instancia = instanciasControlador.get(ruta);
            try {
                String respuesta = ejecutarHandler(metodo, instancia, query);
                enviarRespuesta(salida, 200, "text/html; charset=UTF-8", respuesta.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                String error = "<h1>Error interno: " + e.getMessage() + "</h1>";
                enviarRespuesta(salida, 500, "text/html; charset=UTF-8", error.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            String notFound = "<h1>404 - Recurso no encontrado</h1>";
            enviarRespuesta(salida, 404, "text/html; charset=UTF-8", notFound.getBytes(StandardCharsets.UTF_8));
        }

        conexion.close();
    }

    private void servirArchivoEstatico(String ruta, OutputStream salida) throws IOException {
        // /static/foo.png  ->  static/foo.png  (ruta relativa al classpath)
        String recursoPath = ruta.substring(1);
        InputStream flujo  = getClass().getClassLoader().getResourceAsStream(recursoPath);

        if (flujo == null) {
            byte[] cuerpo = "<h1>404 - Archivo no encontrado</h1>".getBytes(StandardCharsets.UTF_8);
            enviarRespuesta(salida, 404, "text/html; charset=UTF-8", cuerpo);
            return;
        }

        byte[] contenido  = flujo.readAllBytes();
        String contentType = determinarContentType(ruta);
        enviarRespuesta(salida, 200, contentType, contenido);
    }

    private String determinarContentType(String ruta) {
        if (ruta.endsWith(".png"))               return "image/png";
        if (ruta.endsWith(".jpg") || ruta.endsWith(".jpeg")) return "image/jpeg";
        if (ruta.endsWith(".css"))               return "text/css; charset=UTF-8";
        if (ruta.endsWith(".js"))                return "application/javascript";
        if (ruta.endsWith(".html"))              return "text/html; charset=UTF-8";
        return "application/octet-stream";
    }

    private String ejecutarHandler(Method metodo, Object instancia, String query) throws Exception {
        Map<String, String> parametros = parsearParametros(query);
        Parameter[] paramsMetodo = metodo.getParameters();
        Object[] argumentos = new Object[paramsMetodo.length];

        for (int i = 0; i < paramsMetodo.length; i++) {
            RequestParam anotacion = paramsMetodo[i].getAnnotation(RequestParam.class);
            if (anotacion != null) {
                argumentos[i] = parametros.getOrDefault(anotacion.value(), anotacion.defaultValue());
            }
        }
        return (String) metodo.invoke(instancia, argumentos);
    }

    private Map<String, String> parsearParametros(String query) {
        Map<String, String> parametros = new HashMap<>();
        if (query == null || query.isEmpty()) return parametros;
        for (String par : query.split("&")) {
            String[] kv = par.split("=");
            if (kv.length == 2) parametros.put(kv[0], kv[1]);
        }
        return parametros;
    }

    private void enviarRespuesta(OutputStream salida, int codigo, String contentType, byte[] cuerpo) throws IOException {
        String cabeceras = "HTTP/1.1 " + codigo + " OK\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + cuerpo.length + "\r\n"
                + "Connection: close\r\n"
                + "\r\n";
        salida.write(cabeceras.getBytes(StandardCharsets.UTF_8));
        salida.write(cuerpo);
        salida.flush();
    }
}