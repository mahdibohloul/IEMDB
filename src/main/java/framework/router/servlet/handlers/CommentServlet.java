package framework.router.servlet.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.controllers.CommentController;
import application.models.request.VoteCommentRequestModel;
import application.models.response.GenericResponseModel;
import infrastructure.startup.ApplicationStartup;
import infrastructure.workcontext.services.WorkContext;

@WebServlet("/comment/*")
public class CommentServlet extends HttpServlet {
    private final CommentController commentController;
    private final WorkContext workContext;

    public CommentServlet() {
        commentController = ApplicationStartup.getContext().getBean(CommentController.class);
        workContext = ApplicationStartup.getContext().getBean(WorkContext.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer commentId = Integer.parseInt(req.getPathInfo().substring(1));
        String action = req.getParameter("action");
        int movieId = Integer.parseInt(req.getParameter("movie_id"));
        int vote = switch (action) {
            case "like" -> 1;
            case "dislike" -> -1;
            default -> 0;
        };
        VoteCommentRequestModel requestModel =
                new VoteCommentRequestModel(workContext.getCurrentUser().getEmail(), commentId, vote);
        GenericResponseModel responseModel = commentController.voteComment(requestModel);
        if (responseModel.isSuccess()) {
            resp.sendRedirect("/movies/" + movieId);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(req, resp);
        }
    }
}
