package co.edu.escuelaing.controllers;

import co.edu.escuelaing.annotations.GetMapping;
import co.edu.escuelaing.annotations.RequestParam;
import co.edu.escuelaing.annotations.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public String saludar(
            @RequestParam(value = "name", defaultValue = "Mundo") String nombre) {
        return "<h1>Hola, " + nombre + "!</h1>";
    }
}