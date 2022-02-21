package infrastructure.beans;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {
    @Bean
    public Logger getLogger(InjectionPoint injectionPoint) {
        return LoggerFactory
                .getLogger(Objects.requireNonNullElse(injectionPoint.getMethodParameter().getContainingClass(),
                        injectionPoint.getField().getDeclaringClass()));
    }
}
