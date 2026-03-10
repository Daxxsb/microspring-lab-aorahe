package co.edu.escuelaing.controllers;

import co.edu.escuelaing.annotations.GetMapping;
import co.edu.escuelaing.annotations.RestController;

/** Controlador con información de la canción y créditos de producción. */
@RestController
public class SongController {

    @GetMapping("/cancion")
    public String cancion() {
        return """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>aorahe &mdash; Información</title>
                  <link rel="stylesheet" href="/static/style.css">
                </head>
                <body>
                  <nav>
                    <a class="brand" href="/">titirowao</a>
                    <a href="/cancion">Canción</a>
                    <a href="/creditos">Créditos</a>
                  </nav>
                  <div class="container">
                    <div class="seccion-header">
                      <div class="etiqueta">Información</div>
                      <h1>aorahe</h1>
                    </div>
                    <div class="card">
                      <div class="card-title">Detalles del sencillo</div>
                      <div class="campo">
                        <span class="campo-label">Canción</span>
                        <span class="campo-valor">aorahe</span>
                      </div>
                      <div class="campo">
                        <span class="campo-label">Artista</span>
                        <span class="campo-valor">daxxsb</span>
                      </div>
                      <div class="campo">
                        <span class="campo-label">Año</span>
                        <span class="campo-valor">2026</span>
                      </div>
                      <div class="campo">
                        <span class="campo-label">Tipo</span>
                        <span class="campo-valor">Sencillo</span>
                      </div>
                    </div>
                  </div>
                </body>
                </html>
                """;
    }

    @GetMapping("/creditos")
    public String creditos() {
        return """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>aorahe &mdash; Créditos</title>
                  <link rel="stylesheet" href="/static/style.css">
                </head>
                <body>
                  <nav>
                    <a class="brand" href="/">titirowao</a>
                    <a href="/cancion">Canción</a>
                    <a href="/creditos">Créditos</a>
                  </nav>
                  <div class="container">
                    <div class="seccion-header">
                      <div class="etiqueta">Créditos</div>
                      <h1>aorahe</h1>
                    </div>
                    <div class="card">
                      <div class="card-title">Equipo creativo</div>
                      <div class="credito-row">
                        <div class="credito-rol">Autor</div>
                        <div class="credito-nombre">David Salamanca</div>
                        <div class="credito-handle">@daxxsb</div>
                      </div>
                      <div class="credito-row">
                        <div class="credito-rol">Letra, composición e interpretación</div>
                        <div class="credito-nombre">David Salamanca</div>
                        <div class="credito-handle">@daxxsb</div>
                      </div>
                      <div class="credito-row">
                        <div class="credito-rol">Producción musical &middot; Beat, mix &amp; mastering</div>
                        <div class="credito-nombre">Nicolas Martínez</div>
                        <div class="credito-handle">@nico.eqcoo</div>
                      </div>
                    </div>
                  </div>
                </body>
                </html>
                """;
    }
}
