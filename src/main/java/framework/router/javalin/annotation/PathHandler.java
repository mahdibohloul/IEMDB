package framework.router.javalin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.javalin.http.HandlerType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathHandler {
    String path();

    HandlerType[] handlerTypes();
}
