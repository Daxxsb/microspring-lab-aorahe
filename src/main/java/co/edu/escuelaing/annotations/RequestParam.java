package co.edu.escuelaing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Vincula un parámetro de método con un query param de la URL. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
    /** Nombre del parámetro en la query string. */
    String value();
    /** Valor a usar si el parámetro no está presente en la solicitud. */
    String defaultValue() default "";
}