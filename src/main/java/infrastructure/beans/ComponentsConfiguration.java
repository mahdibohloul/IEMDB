package infrastructure.beans;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackages = {"infrastructure", "domain", "application", "framework"})
@EnableAsync
public class ComponentsConfiguration {
}
