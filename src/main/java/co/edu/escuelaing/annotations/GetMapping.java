package co.edu.escuelaing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Marca un método como handler HTTP GET para la ruta especificada. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GetMapping {
    /** Ruta URL que este método va a manejar. */
    String value();
}