package framework.router.servlet.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;
import infrastructure.startup.ApplicationStartup;
import infrastructure.workcontext.services.WorkContext;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final WorkContext workContext;

    public LoginServlet() {
        this.workContext = ApplicationStartup.getContext().getBean(WorkContext.class);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = workContext.setCurrentUser(req.getParameter("email"));
            req.getSession().setAttribute("userEmail", user.getEmail());
            resp.sendRedirect("/");
        } catch (UserNotFoundException e) {
            req.setAttribute("message", "user not found");
            req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }
}
