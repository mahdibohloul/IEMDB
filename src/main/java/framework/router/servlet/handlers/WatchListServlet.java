package framework.router.servlet.handlers;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.controllers.UserController;
import application.models.request.GetUserRecommendedMoviesRequestModel;
import application.models.request.GetUserWatchListRequestModel;
import application.models.response.GenericResponseModel;
import application.models.response.GetUserWatchListResponseModel;
import application.models.response.MovieResponseModel;
import infrastructure.startup.ApplicationStartup;
import infrastructure.workcontext.services.WorkContext;

@WebServlet("/watchList")
public class WatchListServlet extends HttpServlet {
    private final WorkContext workContext;
    private final UserController userController;

    public WatchListServlet() {
        workContext = ApplicationStartup.getContext().getBean(WorkContext.class);
        userController = ApplicationStartup.getContext().getBean(UserController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GetUserWatchListRequestModel requestModel =
                new GetUserWatchListRequestModel(workContext.getCurrentUser().getEmail());
        GenericResponseModel responseModel = userController.getUserWatchList(requestModel);
        GetUserRecommendedMoviesRequestModel recommendedMoviesRequestModel =
                new GetUserRecommendedMoviesRequestModel(workContext.getCurrentUser().getEmail(), 3);
        GenericResponseModel recommendedResponseModel =
                userController.getUserRecommendedMovies(recommendedMoviesRequestModel);
        if (responseModel.isSuccess() && recommendedResponseModel.isSuccess()) {
            GetUserWatchListResponseModel getUserWatchListResponseModel =
                    (GetUserWatchListResponseModel) responseModel.getData();
            List<MovieResponseModel> recommendedMovies = (List<MovieResponseModel>) recommendedResponseModel.getData();
            req.setAttribute("userEmail", workContext.getCurrentUser().getEmail());
            req.setAttribute("userWatchList", getUserWatchListResponseModel);
            req.setAttribute("recommendedMovies", recommendedMovies);
            req.getRequestDispatcher("/WEB-INF/jsp/watchList.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(req, resp);
        }
    }
}
