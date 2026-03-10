package co.edu.escuelaing;

import java.io.File;
import java.lang.reflect.Method;

import co.edu.escuelaing.annotations.GetMapping;
import co.edu.escuelaing.annotations.RestController;
import co.edu.escuelaing.server.HttpServer;

/**
 * Punto de entrada del microframework.
 * Escanea el classpath en busca de clases anotadas con @RestController
 * y registra sus rutas en el servidor HTTP.
 */
public class MicroSpringBoot {

    public static void main(String[] args) throws Exception {
        HttpServer servidor = new HttpServer(8080);

        if (args.length > 0) {
            registrarControlador(args[0], servidor);
        } else {
            explorarClasspath(servidor);
        }

        servidor.iniciar();
    }

    private static void registrarControlador(String nombreClase, HttpServer servidor) throws Exception {
        Class<?> clase = Class.forName(nombreClase);

        if (!clase.isAnnotationPresent(RestController.class)) return;

        Object instancia = clase.getDeclaredConstructor().newInstance();

        for (Method metodo : clase.getDeclaredMethods()) {
            if (metodo.isAnnotationPresent(GetMapping.class)) {
                String ruta = metodo.getAnnotation(GetMapping.class).value();
                servidor.agregarRuta(ruta, metodo, instancia);
                System.out.println("Ruta registrada: GET " + ruta + " -> " + nombreClase);
            }
        }
    }

    private static void explorarClasspath(HttpServer servidor) throws Exception {
        String classpath = System.getProperty("java.class.path");
        for (String entrada : classpath.split(File.pathSeparator)) {
            File archivo = new File(entrada);
            if (archivo.isDirectory()) {
                explorarDirectorio(archivo, archivo, servidor);
            }
        }
    }

    private static void explorarDirectorio(File raiz, File directorio, HttpServer servidor) throws Exception {
        for (File archivo : directorio.listFiles()) {
            if (archivo.isDirectory()) {
                explorarDirectorio(raiz, archivo, servidor);
            } else if (archivo.getName().endsWith(".class")) {
                String nombreClase = raiz.toURI().relativize(archivo.toURI())
                    .getPath()
                    .replace("/", ".")
                    .replace(".class", "");
                try {
                    registrarControlador(nombreClase, servidor);
                } catch (Exception ignorada) {}
            }
        }
    }
}