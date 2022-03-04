package infrastructure.runner;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import framework.router.javalin.JavalinRouter;
import infrastructure.startup.ApplicationStartup;
import io.javalin.Javalin;
import io.javalin.http.HttpCode;

@Service(value = "ApplicationJavalinRunner")
@Lazy
public class ApplicationJavalinRunner implements ApplicationRunner {

    private final Javalin javalinApp;
    private final JavalinRouter javalinRouter;

    public ApplicationJavalinRunner(Javalin javalinApp, JavalinRouter javalinRouter) {
        this.javalinApp = javalinApp;
        this.javalinRouter = javalinRouter;
    }

    @Override
    public void run() {
        javalinApp.get("/movies", javalinRouter::route)
                .error(HttpCode.NOT_FOUND.getStatus(), javalinRouter::indexNotFound)
                .error(HttpCode.FORBIDDEN.getStatus(), javalinRouter::indexForbidden)
                .get("/movies/{movie_id}", javalinRouter::route)
                .post("/voteComment/{user_id}/{comment_id}/{vote}", javalinRouter::route)
                .post("/watchList/{user_id}/{movie_id}", javalinRouter::route)
                .post("/rateMovie/{user_id}/{movie_id}/{rate}", javalinRouter::route)
                .post("/index/success", javalinRouter::route)
                .get("/index/success", javalinRouter::route)
                .get("/", javalinRouter::route);
    }

    @Override
    public void stop() {
        Runtime.getRuntime().addShutdownHook(new Thread(ApplicationStartup::stop));
    }

}
