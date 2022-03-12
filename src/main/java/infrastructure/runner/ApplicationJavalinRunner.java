//package infrastructure.runner;
//
//import org.springframework.context.annotation.Lazy;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Service;
//
//import framework.router.javalin.JavalinRouter;
//import infrastructure.startup.ApplicationStartup;
//import io.javalin.Javalin;
//import io.javalin.http.HttpCode;
//
//@Service(value = "ApplicationJavalinRunner")
//@Lazy
//@Profile("javalin")
//public class ApplicationJavalinRunner implements ApplicationRunner {
//
//    private final Javalin javalinApp;
//    private final JavalinRouter javalinRouter;
//
//    public ApplicationJavalinRunner(Javalin javalinApp, JavalinRouter javalinRouter) {
//        this.javalinApp = javalinApp;
//        this.javalinRouter = javalinRouter;
//    }
//
//    @Override
//    public void run() {
//        javalinApp.get("/movies", javalinRouter::route)
//                .error(HttpCode.NOT_FOUND.getStatus(), javalinRouter::indexNotFound)
//                .error(HttpCode.FORBIDDEN.getStatus(), javalinRouter::indexForbidden)
//                .post("/voteComment/{user_id}/{comment_id}/{vote}", javalinRouter::route)
//                .get("/voteComment/{user_id}/{comment_id}/{vote}", javalinRouter::route)
//                .post("/watchList/{user_id}/{movie_id}", javalinRouter::route)
//                .get("/watchList/{user_id}/{movie_id}", javalinRouter::route)
//                .post("/rateMovie/{user_id}/{movie_id}/{rate}", javalinRouter::route)
//                .get("/rateMovie/{user_id}/{movie_id}/{rate}", javalinRouter::route)
//                .post("/index/success", javalinRouter::route)
//                .get("/", javalinRouter::route)
//                .get("/actors/{actor_id}", javalinRouter::route)
//                .get("/movies/{movie_id}", javalinRouter::route)
//                .get("/movies/search/{start_year}/{end_year}", javalinRouter::route)
//                .get("/movies/search/{genre}", javalinRouter::route)
//                .get("/watchList/{user_id}", javalinRouter::route)
//                .get("/index/success", javalinRouter::route)
//                .get("/watchList/{user_id}/{movie_id}/remove", javalinRouter::route);
//    }
//
//    @Override
//    public void stop() {
//        Runtime.getRuntime().addShutdownHook(new Thread(ApplicationStartup::stop));
//    }
//
//}
