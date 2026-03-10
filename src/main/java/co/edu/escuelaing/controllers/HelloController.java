package co.edu.escuelaing.controllers;

import co.edu.escuelaing.annotations.GetMapping;
import co.edu.escuelaing.annotations.RestController;

/** Controlador raíz: sirve la página principal con la portada del sencillo. */
@RestController
public class HelloController {

    @GetMapping("/")
    public String inicio() {
        return """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>aorahe &mdash; daxxsb</title>
                  <link rel="stylesheet" href="/static/style.css">
                </head>
                <body>
                  <nav>
                    <a class="brand" href="/">titirowao</a>
                    <a href="/cancion">Canción</a>
                    <a href="/creditos">Créditos</a>
                  </nav>
                  <div class="container">
                    <div class="hero">
                      <img class="portada" src="/static/aorahe.png" alt="Portada de aorahe">
                      <div class="hero-meta">
                        <div class="tipo">Sencillo &middot; 2026</div>
                        <h1>aorahe</h1>
                        <div class="artista">daxxsb</div>
                        <div class="anio">&copy; 2026 David Salamanca</div>
                        <a class="btn" href="/cancion">Ver detalles</a>
                        <a class="btn btn-outline" href="/creditos">Créditos</a>
                        <a class="btn btn-outline" href="/static/aorahe.png" target="_blank">Ver portada</a>
                      </div>
                    </div>
                  </div>
                </body>
                </html>
                """;
    }
}