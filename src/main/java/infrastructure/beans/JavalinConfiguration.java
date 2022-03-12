//package infrastructure.beans;
//
//import io.javalin.Javalin;
//import io.javalin.plugin.openapi.OpenApiOptions;
//import io.javalin.plugin.openapi.OpenApiPlugin;
//import io.javalin.plugin.openapi.ui.SwaggerOptions;
//import io.swagger.v3.oas.models.info.Info;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import javax.annotation.PreDestroy;
//
//@Configuration
//public class JavalinConfiguration {
//    private Javalin app;
//
//    @Bean
//    @Profile("javalin")
//    public Javalin javalin() {
//        if (app == null) {
//            app = Javalin.create(config -> {
//                config.registerPlugin(new OpenApiPlugin(getOpenApiOptions()));
//            }).start(8000);
//        }
//        return app;
//    }
//
//    private OpenApiOptions getOpenApiOptions() {
//        Info info = new Info()
//                .version("1.0.0")
//                .description("IEMDB API");
//        return new OpenApiOptions(info).path("/swagger-docs")
//                .swagger(new SwaggerOptions("/swagger").title("IEMDB API"));
//    }
//
//    @PreDestroy
//    private void destroy() {
//        if (app != null) {
//            app.stop();
//        }
//    }
//}
