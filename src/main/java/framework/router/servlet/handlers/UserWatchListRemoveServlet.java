package framework.router.servlet.handlers;

import application.controllers.UserController;
import application.models.request.RemoveUserWatchListRequestModel;
import application.models.response.GenericResponseModel;
import infrastructure.startup.ApplicationStartup;
import infrastructure.workcontext.services.WorkContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/watchList/remove")
public class UserWatchListRemoveServlet extends HttpServlet {
    private final WorkContext workContext;
    private final UserController userController;

    public UserWatchListRemoveServlet() {
        workContext = ApplicationStartup.getContext().getBean(WorkContext.class);
        userController = ApplicationStartup.getContext().getBean(UserController.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer movieId = Integer.valueOf(req.getParameter("movie_id"));
        RemoveUserWatchListRequestModel requestModel = new RemoveUserWatchListRequestModel(
                workContext.getCurrentUser().getEmail(),
                movieId
        );
        GenericResponseModel responseModel = userController.removeFromWatchList(requestModel);
        if (responseModel.isSuccess()) {
            resp.sendRedirect("/watchList");
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(req, resp);
        }
    }
}
