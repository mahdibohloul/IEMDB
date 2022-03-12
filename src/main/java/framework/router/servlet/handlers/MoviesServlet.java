package framework.router.servlet.handlers;

import application.controllers.MovieController;
import application.models.response.MoviesResponseModel;
import domain.movie.valueobjects.MovieSearchModel;
import domain.movie.valueobjects.MovieSortOption;
import infrastructure.startup.ApplicationStartup;
import infrastructure.workcontext.services.WorkContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/movies")
public class MoviesServlet extends HttpServlet {
    private final WorkContext workContext;
    private final MovieController movieController;
    private MovieSortOption movieSortOption = MovieSortOption.IMDB;
    private String searchQuery;

    public MoviesServlet() {
        workContext = ApplicationStartup.getContext().getBean(WorkContext.class);
        movieController = ApplicationStartup.getContext().getBean(MovieController.class);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MoviesResponseModel moviesResponseModel = movieController.getMoviesList();
        req.setAttribute("userEmail", workContext.getCurrentUser().getEmail());
        req.setAttribute("movies", moviesResponseModel.getMoviesList());
        req.getRequestDispatcher("WEB-INF/jsp/movies.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MovieSearchModel searchModel = new MovieSearchModel();
        String action = req.getParameter("action");
        switch (action) {
            case "search" -> {
                String movieName = req.getParameter("search");
                searchQuery = movieName;
                searchModel.setNames(List.of(movieName));
                searchModel.setSortOption(movieSortOption);
            }
            case "sort_by_imdb" -> {
                movieSortOption = MovieSortOption.IMDB;
                searchModel.setSortOption(MovieSortOption.IMDB);
                searchModel.setNames(List.of(searchQuery));
            }
            case "sort_by_date" -> {
                searchModel.setSortOption(MovieSortOption.RELEASE_DATE_YEAR);
                movieSortOption = MovieSortOption.RELEASE_DATE_YEAR;
                searchModel.setNames(List.of(searchQuery));
            }
            case "clear" -> {
                movieSortOption = MovieSortOption.IMDB;
                searchQuery = null;
            }
        }
        MoviesResponseModel moviesResponseModel = movieController.searchMovies(searchModel);
        req.setAttribute("userEmail", workContext.getCurrentUser().getEmail());
        req.setAttribute("searchQuery", searchQuery);
        req.setAttribute("movies", moviesResponseModel.getMoviesList());
        req.getRequestDispatcher("WEB-INF/jsp/movies.jsp").forward(req, resp);
    }
}
