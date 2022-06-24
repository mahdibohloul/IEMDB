package framework.router.servlet.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.controllers.MovieController;
import application.controllers.UserController;
import application.models.request.AddCommentRequestModel;
import application.models.request.AddUserWatchListRequestModel;
import application.models.request.GetMovieByIdRequestModel;
import application.models.request.RateMovieRequestModel;
import application.models.response.GenericResponseModel;
import application.models.response.MovieDetailResponseModel;
import infrastructure.startup.ApplicationStartup;
import infrastructure.workcontext.services.WorkContext;

@WebServlet("/movies/*")
public class MovieServlet extends HttpServlet {
    private final WorkContext workContext;
    private final MovieController movieController;
    private final UserController userController;

    public MovieServlet() {
        workContext = ApplicationStartup.getContext().getBean(WorkContext.class);
        movieController = ApplicationStartup.getContext().getBean(MovieController.class);
        userController = ApplicationStartup.getContext().getBean(UserController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer movieId = Integer.valueOf(req.getPathInfo().substring(1));
        GetMovieByIdRequestModel requestModel = new GetMovieByIdRequestModel(movieId);
        GenericResponseModel genericResponseModel = movieController.getMovieById(requestModel);
        if (genericResponseModel.isSuccess()) {
            req.setAttribute("userEmail", workContext.getCurrentUser().getEmail());
            MovieDetailResponseModel detail = (MovieDetailResponseModel) genericResponseModel.getData();
            req.setAttribute("movie", detail);
            req.getRequestDispatcher("/WEB-INF/jsp/movie.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "rate" -> rateMovie(req, resp);
            case "add" -> addMovieToWatchList(req, resp);
            case "comment" -> addComment(req, resp);
        }
    }

    private void addComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer movieId = Integer.valueOf(req.getPathInfo().substring(1));
        String comment = req.getParameter("comment");
        AddCommentRequestModel requestModel = new AddCommentRequestModel(
                workContext.getCurrentUser().getEmail(),
                movieId,
                comment
        );
        GenericResponseModel responseModel = movieController.addComment(requestModel);
        if (responseModel.isSuccess()) {
            resp.sendRedirect("/movies/" + movieId);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(req, resp);
        }
    }

    private void rateMovie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer movieId = Integer.valueOf(req.getPathInfo().substring(1));
        Integer quantity = Integer.valueOf(req.getParameter("quantity"));
        RateMovieRequestModel requestModel = new RateMovieRequestModel();
        requestModel.setMovieId(movieId);
        requestModel.setScore(quantity);
        requestModel.setUserEmail(workContext.getCurrentUser().getEmail());
        GenericResponseModel responseModel = movieController.rateMovie(requestModel);
        if (responseModel.isSuccess()) {
            resp.sendRedirect("/movies/" + movieId);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(req, resp);
        }
    }

    private void addMovieToWatchList(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Integer movieId = Integer.valueOf(req.getPathInfo().substring(1));
        AddUserWatchListRequestModel requestModel =
                new AddUserWatchListRequestModel(workContext.getCurrentUser().getEmail(),
                        movieId);
        GenericResponseModel responseModel = userController.addToWatchList(requestModel);
        if (responseModel.isSuccess()) {
            resp.sendRedirect("/movies/" + movieId);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(req, resp);
        }
    }
}
