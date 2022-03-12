package framework.router.servlet.handlers;

import application.controllers.ActorController;
import application.models.request.GetActorByIdRequestModel;
import application.models.response.ActorDetailResponseModel;
import domain.actor.exceptions.ActorNotFoundException;
import infrastructure.startup.ApplicationStartup;
import infrastructure.workcontext.services.WorkContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/actors/*")
public class ActorServlet extends HttpServlet {
    private final WorkContext workContext;
    private final ActorController actorController;

    public ActorServlet() {
        workContext = ApplicationStartup.getContext().getBean(WorkContext.class);
        actorController = ApplicationStartup.getContext().getBean(ActorController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer actorId = Integer.valueOf(req.getPathInfo().substring(1));
        GetActorByIdRequestModel getActorByIdRequestModel = new GetActorByIdRequestModel(actorId);
        try {
            ActorDetailResponseModel responseModel = actorController.getActorById(getActorByIdRequestModel);
            req.setAttribute("userEmail", workContext.getCurrentUser().getEmail());
            req.setAttribute("actor", responseModel);
            req.getRequestDispatcher("/WEB-INF/jsp/actor.jsp").forward(req, resp);
        } catch (ActorNotFoundException e) {
            req.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(req, resp);
        }
    }
}
