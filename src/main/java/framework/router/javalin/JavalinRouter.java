//package framework.router.javalin;
//
//import application.controllers.ActorController;
//import application.controllers.CommentController;
//import application.controllers.MovieController;
//import application.controllers.UserController;
//import application.models.request.*;
//import application.models.response.*;
//import domain.actor.exceptions.ActorNotFoundException;
//import framework.router.javalin.annotation.PathHandler;
//import framework.router.javalin.exceptions.PathNotFoundException;
//import io.javalin.http.Context;
//import io.javalin.http.HandlerType;
//import io.javalin.http.HttpCode;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//
//@Component
//@Profile("javalin")
//public class JavalinRouter {
//    private final MovieController movieController;
//    private final CommentController commentController;
//    private final UserController userController;
//    private final ActorController actorController;
//
//    public JavalinRouter(MovieController movieController, CommentController commentController,
//            UserController userController, ActorController actorController) {
//        this.movieController = movieController;
//        this.commentController = commentController;
//        this.userController = userController;
//        this.actorController = actorController;
//    }
//
//
//    public void route(Context context) throws IOException {
//        try {
//            Method handlerFunction = getHandlerFunction(context.matchedPath(), context.handlerType());
//            handlerFunction.invoke(this, context);
//        } catch (PathNotFoundException e) {
//            indexNotFound(context);
//        } catch (Exception e) {
//            context.status(HttpCode.INTERNAL_SERVER_ERROR);
//            context.result(e.getMessage());
//        }
//    }
//
//    @PathHandler(path = "/index/forbidden", handlerTypes = {HandlerType.GET, HandlerType.POST})
//    public void indexForbidden(Context context) throws IOException {
//        Document document = Jsoup.parse(new File("src/main/resources/templates/403.html"), "UTF-8");
//        context.status(HttpCode.FORBIDDEN);
//        context.html(document.toString());
//    }
//
//    @PathHandler(path = "/index/notfound", handlerTypes = {HandlerType.GET, HandlerType.POST})
//    public void indexNotFound(Context context) throws IOException {
//        Document document = Jsoup.parse(new File("src/main/resources/templates/404.html"), "UTF-8");
//        context.status(HttpCode.NOT_FOUND);
//        context.html(document.toString());
//    }
//
//    @PathHandler(path = "/index/success", handlerTypes = {HandlerType.GET, HandlerType.POST})
//    private void indexSuccess(Context context) throws IOException {
//        Document document = Jsoup.parse(new File("src/main/resources/templates/200.html"), "UTF-8");
//        context.status(HttpCode.OK);
//        context.html(document.toString());
//    }
//
//    @PathHandler(path = "/watchList/{user_id}/{movie_id}/remove", handlerTypes = HandlerType.GET)
//    private void removeUserWatchList(Context context) throws IOException {
//        String userEmail = context.pathParamAsClass("user_id", String.class).get();
//        Integer movieId = context.pathParamAsClass("movie_id", Integer.class).get();
//        RemoveUserWatchListRequestModel request = new RemoveUserWatchListRequestModel(userEmail, movieId);
//        GenericResponseModel responseModel = userController.removeFromWatchList(request);
//        handleGenericResponseModel(responseModel, context);
//    }
//
//    @PathHandler(path = "/watchList/{user_id}", handlerTypes = HandlerType.GET)
//    private void getUserWatchList(Context context) throws IOException {
//        String userEmail = context.pathParamAsClass("user_id", String.class).
//                check(it -> it != null && !it.isEmpty() && !it.isBlank(), "User id is required").get();
//        GetUserWatchListRequestModel request = new GetUserWatchListRequestModel(userEmail);
//        GenericResponseModel response = userController.getUserWatchList(request);
//        if (response.isSuccess()) {
//            GetUserWatchListResponseModel responseModel = (GetUserWatchListResponseModel) response.getData();
//            context.html(prepareUserWatchListDetailPage(responseModel));
//            context.status(HttpCode.OK);
//        } else
//            handleGenericResponseModel(response, context);
//    }
//
//    @PathHandler(path = "/movies/search/{genre}", handlerTypes = HandlerType.GET)
//    private void searchMoviesByGenre(Context context) throws IOException {
//        String genre = context.pathParam("genre");
//        GetMoviesByGenreRequestModel request = new GetMoviesByGenreRequestModel(genre);
//        MoviesResponseModel response = movieController.getMoviesByGenre(request);
//        context.html(prepareMovieListPage(response.getMoviesList()));
//        context.status(HttpCode.OK);
//    }
//
//    @PathHandler(path = "/movies/search/{start_year}/{end_year}", handlerTypes = HandlerType.GET)
//    private void searchMoviesByYear(Context context) throws IOException {
//        Integer startYear = context.pathParamAsClass("start_year", Integer.class).get();
//        Integer endYear = context.pathParamAsClass("end_year", Integer.class).get();
//        GetMoviesByYearRequestModel request = new GetMoviesByYearRequestModel(startYear, endYear);
//        MoviesResponseModel response = movieController.getMoviesByYear(request);
//        context.html(prepareMovieListPage(response.getMoviesList()));
//        context.status(HttpCode.OK);
//    }
//
//    @PathHandler(path = "/watchList/{user_id}/{movie_id}", handlerTypes = {HandlerType.POST, HandlerType.GET})
//    private void addWatchList(Context context) throws IOException {
//        String userEmail = context.formParamMap().containsKey("user_id") ? context.formParam("user_id") :
//                context.pathParam("user_id");
//        Integer movieId = context.formParamMap().containsKey("movie_id") ?
//                Integer.valueOf(Objects.requireNonNull(context.formParam("movie_id"))) :
//                Integer.valueOf(context.pathParam("movie_id"));
//        AddUserWatchListRequestModel request = new AddUserWatchListRequestModel(userEmail, movieId);
//        GenericResponseModel response = userController.addToWatchList(request);
//        handleGenericResponseModel(response, context);
//    }
//
//    @PathHandler(path = "/rateMovie/{user_id}/{movie_id}/{rate}", handlerTypes = {HandlerType.POST, HandlerType.GET})
//    private void rateMovie(Context context) throws IOException {
//
//        String userEmail = context.formParamMap().containsKey("user_id") ? context.formParam("user_id") :
//                context.pathParam("user_id");
//        Integer movieId = context.formParamMap().containsKey("movie_id") ?
//                Integer.valueOf(Objects.requireNonNull(context.formParam("movie_id"))) :
//                Integer.valueOf(context.pathParam("movie_id"));
//        Integer rate = context.formParamMap().containsKey("quantity") ?
//                Integer.valueOf(Objects.requireNonNull(context.formParam("quantity"))) :
//                Integer.valueOf(context.pathParam("rate"));
//        RateMovieRequestModel request = new RateMovieRequestModel(userEmail, movieId, rate);
//        GenericResponseModel response = movieController.rateMovie(request);
//        handleGenericResponseModel(response, context);
//    }
//
//    @PathHandler(path = "/voteComment/{user_id}/{comment_id}/{vote}", handlerTypes = {HandlerType.POST, HandlerType.GET})
//    private void voteComment(Context context) throws IOException {
//        String userEmail = context.pathParam("user_id");
//        Integer commentId = Integer.valueOf(context.pathParam("comment_id"));
//        Integer vote = Integer.valueOf(context.pathParam("vote"));
//        VoteCommentRequestModel request = new VoteCommentRequestModel(userEmail, commentId, vote);
//        GenericResponseModel response = commentController.voteComment(request);
//        handleGenericResponseModel(response, context);
//    }
//
//    @PathHandler(path = "/actors/{actor_id}", handlerTypes = HandlerType.GET)
//    private void getActorDetail(Context context) throws IOException {
//        Integer actorId = Integer.valueOf(Objects.requireNonNull(context.pathParam("actor_id")));
//        GetActorByIdRequestModel request = new GetActorByIdRequestModel(actorId);
//        try {
//            ActorDetailResponseModel response = actorController.getActorById(request);
//            context.html(prepareActorDetailPage(response));
//            context.status(HttpCode.OK);
//        } catch (ActorNotFoundException e) {
//            context.result("Actor not found");
//            indexNotFound(context);
//        }
//    }
//
//    @PathHandler(path = "/movies/{movie_id}", handlerTypes = HandlerType.GET)
//    private void getMovieDetail(Context context) throws IOException {
//        Integer movieId = Integer.valueOf(Objects.requireNonNull(context.pathParam("movie_id")));
//        GetMovieByIdRequestModel getMovieByIdRequestModel = new GetMovieByIdRequestModel(movieId);
//        GenericResponseModel responseModel = movieController.getMovieById(getMovieByIdRequestModel);
//        if (responseModel.isSuccess()) {
//            MovieDetailResponseModel movieResponseModel = (MovieDetailResponseModel) responseModel.getData();
//            context.html(prepareMovieDetailPage(movieResponseModel));
//        } else if (responseModel.getData().toString().equals("MovieNotFound")) {
//            context.status(HttpCode.NOT_FOUND);
//            context.result("Movie not found");
//        } else {
//            context.status(HttpCode.INTERNAL_SERVER_ERROR);
//            context.result(responseModel.getData().toString());
//        }
//    }
//
//    @PathHandler(path = "/", handlerTypes = HandlerType.GET)
//    private void home(Context context) {
//        context.redirect("/swagger", HttpCode.TEMPORARY_REDIRECT.getStatus());
//    }
//
//    @PathHandler(path = "/movies", handlerTypes = HandlerType.GET)
//    private void getMovies(Context context) throws IOException {
//        MoviesResponseModel moviesResponseModel = movieController.getMoviesList();
//        context.html(prepareMovieListPage(moviesResponseModel.getMoviesList()));
//        context.status(HttpCode.OK);
//    }
//
//
//    private void handleGenericResponseModel(GenericResponseModel response, Context context) throws IOException {
//        if (response.isSuccess()) {
//            context.result(response.getData().toString());
//            indexSuccess(context);
//        } else if (response.getData().toString().equals("MovieNotFound")) {
//            context.status(HttpCode.NOT_FOUND);
//            context.result("Movie not found");
//        } else if (response.getData().toString().equals("UserNotFound")) {
//            context.status(HttpCode.NOT_FOUND);
//            context.result("User not found");
//        } else if (response.getData().toString().equals("AgeLimitError")) {
//            context.status(HttpCode.FORBIDDEN);
//            context.result("Age limit error");
//        } else if (response.getData().toString().equals("ActorNotFound")) {
//            context.status(HttpCode.NOT_FOUND);
//            context.result("Actor not found");
//        } else if (response.getData().toString().equals("MovieAlreadyExists")) {
//            context.status(HttpCode.FORBIDDEN);
//            context.result("Movie already exists");
//        } else {
//            context.status(HttpCode.INTERNAL_SERVER_ERROR);
//            context.result(response.getData().toString());
//        }
//    }
//
//
//    private Method getHandlerFunction(String path, HandlerType handlerType) throws PathNotFoundException {
//        Method[] methods = this.getClass().getDeclaredMethods();
//        return Arrays.stream(methods).toList()
//                .stream().filter(method -> method.isAnnotationPresent(PathHandler.class))
//                .filter(method -> method.getAnnotation(PathHandler.class).path().equals(path) &&
//                                  Arrays.stream(method.getAnnotation(PathHandler.class).handlerTypes()).toList()
//                                          .contains(handlerType))
//                .findFirst().orElseThrow(() -> new PathNotFoundException(path));
//    }
//
//    private String prepareMovieDetailPage(MovieDetailResponseModel movieResponseModel) throws IOException {
//        Document document = Jsoup.parse(new File("src/main/resources/templates/movie.html"), "UTF-8");
//        Element ul = Objects.requireNonNull(document.selectFirst("body")).selectFirst("ul");
//        ul.select("li").forEach(li -> {
//            if (li.id().equals("name"))
//                li.text("name: " + movieResponseModel.getName());
//            else if (li.id().equals("summary"))
//                li.text("summary: " + movieResponseModel.getSummary());
//            else if (li.id().equals("releaseDate"))
//                li.text("releaseDate: " + movieResponseModel.getReleaseDate());
//            else if (li.id().equals("director"))
//                li.text("director: " + movieResponseModel.getDirector());
//            else if (li.id().equals("writers"))
//                li.text("writers: " + movieResponseModel.getWriters());
//            else if (li.id().equals("genres"))
//                li.text("genres: " + movieResponseModel.getGenres());
//            else if (li.id().equals("cast"))
//                li.text("cast: " + movieResponseModel.getCast().stream().map(ActorResponseModel::getName).toList());
//            else if (li.id().equals("imdbRate"))
//                li.text("imdbRate: " + movieResponseModel.getImdbRate());
//            else if (li.id().equals("rating"))
//                li.text("rating: " + movieResponseModel.getRating());
//            else if (li.id().equals("duration"))
//                li.text("duration: " + movieResponseModel.getDuration());
//            else if (li.id().equals("ageLimit"))
//                li.text("ageLimit: " + movieResponseModel.getAgeLimit());
//        });
//        Element rateMovieForm = document.selectFirst("form[name=rateMovieForm]");
//        rateMovieForm.append(
//                "<input type=\"hidden\" name=\"movie_id\" value=\"" + movieResponseModel.getMovieId() + "\">");
//        Element addWatchListForm = document.selectFirst("form[name=addWatchListForm]");
//        addWatchListForm.append(
//                "<input type=\"hidden\" name=\"movie_id\" value=\"" + movieResponseModel.getMovieId() + "\">");
//        Element table = document.selectFirst("table");
//        Element tbody = table.select("tbody").get(0);
//        Elements rows = table.select("tr");
//        Element headRow = rows.get(0);
//        StringBuilder sb = new StringBuilder();
//        movieResponseModel.getComments().forEach(commentResponseModel -> {
//            sb.append("<tr>");
//            headRow.select("th").forEach(th -> {
//                if (th.text().equals("nickname"))
//                    sb.append("<td>").append("@").append(commentResponseModel.getUserNickname()).append("</td>");
//                else if (th.text().equals("comment"))
//                    sb.append("<td>").append(commentResponseModel.getText()).append("</td>");
//                else if (th.text().equals("like"))
//                    sb.append("<td>")
//                            .append("<form action=\"/voteComment/").append(commentResponseModel.getUserEmail())
//                            .append("/").append(commentResponseModel.getCommentId()).append("/1")
//                            .append("\" method=\"POST\">")
//                            .append("<label for=\"like\">").append(commentResponseModel.getLike())
//                            .append("</label>")
//                            .append("<input type=\"submit\" value=\"Like\">")
//                            .append("</td>");
//                else if (th.text().equals("dislike"))
//                    sb.append("<td>")
//                            .append("<form action=\"/voteComment/").append(commentResponseModel.getUserEmail())
//                            .append("/").append(commentResponseModel.getCommentId()).append("/-1")
//                            .append("\" method=\"POST\">")
//                            .append("<label for=\"dislike\">").append(commentResponseModel.getDislike())
//                            .append("</label>")
//                            .append("<input type=\"submit\" value=\"Dislike\">")
//                            .append("</td>");
//
//            });
//            sb.append("<td hidden>").append(commentResponseModel.getUserEmail()).append("</td>");
//            sb.append("</tr>");
//            tbody.append(sb.toString());
//            sb.setLength(0);
//        });
//        return document.toString();
//    }
//
//    private String prepareMovieListPage(List<MovieResponseModel> movies) throws IOException {
//        Document document = Jsoup.parse(new File("src/main/resources/templates/movies.html"), "UTF-8");
//        Element table = document.select("table").get(0);
//        Element tbody = table.select("tbody").get(0);
//        Elements rows = table.select("tr");
//        StringBuilder sb = new StringBuilder();
//        movies.forEach(movie -> {
//            sb.append("<tr>");
//            rows.get(0).select("th").forEach(th -> {
//                if (th.text().equals("name"))
//                    sb.append("<td>").append(movie.getName()).append("</td>");
//                else if (th.text().equals("summary"))
//                    sb.append("<td>").append(movie.getSummary()).append("</td>");
//                else if (th.text().equals("releaseDate"))
//                    sb.append("<td>").append(new SimpleDateFormat("dd-MM-yyyy").format(movie.getReleaseDate()))
//                            .append("</td>");
//                else if (th.text().equals("director"))
//                    sb.append("<td>").append(movie.getDirector()).append("</td>");
//                else if (th.text().equals("writers"))
//                    sb.append("<td>").append(movie.getWriters()).append("</td>");
//                else if (th.text().equals("genres"))
//                    sb.append("<td>").append(movie.getGenres()).append("</td>");
//                else if (th.text().equals("cast"))
//                    sb.append("<td>").append(movie.getCast()).append("</td>");
//                else if (th.text().equals("imdb Rate"))
//                    sb.append("<td>").append(movie.getImdbRate()).append("</td>");
//                else if (th.text().equals("rating"))
//                    sb.append("<td>").append(movie.getRating()).append("</td>");
//                else if (th.text().equals("duration"))
//                    sb.append("<td>").append(movie.getDuration()).append("</td>");
//                else if (th.text().equals("ageLimit"))
//                    sb.append("<td>").append(movie.getAgeLimit()).append("</td>");
//                else if (th.text().equals("Links"))
//                    sb.append("<td>").append("<a href=\"/movies/").append(movie.getMovieId())
//                            .append("\">Details</a>").append("</td>");
//            });
//            sb.append("</tr>");
//            tbody.append(sb.toString());
//            sb.setLength(0);
//        });
//        return document.toString();
//    }
//
//    private String prepareActorDetailPage(ActorDetailResponseModel actor) throws IOException {
//        Document document = Jsoup.parse(new File("src/main/resources/templates/actor.html"), "UTF-8");
//        Element ul = Objects.requireNonNull(document.selectFirst("body")).selectFirst("ul");
//        ul.select("li").forEach(li -> {
//            if (li.id().equals("name"))
//                li.text("name: " + actor.getName());
//            else if (li.id().equals("birthDate"))
//                li.text("birthDate: " + actor.getBirthDate());
//            else if (li.id().equals("nationality"))
//                li.text("nationality: " + actor.getNationality());
//            else if (li.id().equals("tma"))
//                li.text("Total movies acted in: " + actor.getMovies().size());
//        });
//        Element table = document.selectFirst("table");
//        Element tbody = table.selectFirst("tbody");
//        Elements rows = tbody.select("tr");
//        Element header = rows.get(0);
//        StringBuilder sb = new StringBuilder();
//        actor.getMovies().forEach(movie -> {
//            sb.append("<tr>");
//            header.select("th").forEach(th -> {
//                if (th.text().equals("Movie"))
//                    sb.append("<td>").append(movie.getName()).append("</td>");
//                else if (th.text().equals("imdb Rate"))
//                    sb.append("<td>").append(movie.getImdbRate()).append("</td>");
//                else if (th.text().equals("rating"))
//                    sb.append("<td>").append(movie.getRating()).append("</td>");
//                else if (th.text().equals("page"))
//                    sb.append("<td>").append("<a href=\"/movies/").append(movie.getId()).append("\">")
//                            .append("Link")
//                            .append("</a>").append("</td>");
//            });
//            sb.append("</tr>");
//            tbody.append(sb.toString());
//            sb.setLength(0);
//        });
//        return document.toString();
//    }
//
//    private String prepareUserWatchListDetailPage(GetUserWatchListResponseModel response) throws IOException {
//        Document document = Jsoup.parse(new File("src/main/resources/templates/watchlist.html"), "UTF-8");
//        Element ul = document.selectFirst("ul");
//        ul.select("li").forEach(li -> {
//            if (li.id().equals("name")) li.text(response.getUserName());
//            else if (li.id().equals("nickname")) li.text("@" + response.getUserNickname());
//        });
//        Element table = document.selectFirst("table");
//        Element tbody = table.selectFirst("tbody");
//        Element header = tbody.selectFirst("tr");
//        StringBuilder sb = new StringBuilder();
//        response.getWatchList().forEach(movie -> {
//            sb.append("<tr>");
//            header.select("th").forEach(th -> {
//                if (th.text().equals("Movie"))
//                    sb.append("<td>").append(movie.getName()).append("</td>");
//                else if (th.text().equals("releaseDate"))
//                    sb.append("<td>").append(new SimpleDateFormat("yyyy-MM-dd").format(movie.getReleaseDate()))
//                            .append("</td>");
//                else if (th.text().equals("director"))
//                    sb.append("<td>").append(movie.getDirector()).append("</td>");
//                else if (th.text().equals("genres"))
//                    sb.append("<td>").append(movie.getGenres()).append("</td>");
//                else if (th.text().equals("imdb Rate"))
//                    sb.append("<td>").append(movie.getImdbRate()).append("</td>");
//                else if (th.text().equals("rating"))
//                    sb.append("<td>").append(movie.getRating()).append("</td>");
//                else if (th.text().equals("duration"))
//                    sb.append("<td>").append(movie.getDuration()).append("</td>");
//                else if (th.text().equals("goto"))
//                    sb.append("<td><a href=\"/movies/").append(movie.getMovieId()).append("\">").append("Go to movie")
//                            .append("</a></td>");
//                else if (th.text().equals("remove"))
//                    sb.append("<td><a href=\"/watchList/").append(response.getUserEmail()).append("/")
//                            .append(movie.getMovieId()).append("/remove").append("\">").append("Remove")
//                            .append("</a></td>");
//            });
//            tbody.append(sb.toString());
//            sb.setLength(0);
//        });
//        return document.toString();
//    }
//
//}
