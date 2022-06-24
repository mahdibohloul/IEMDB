package framework.router.servlet.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import infrastructure.startup.ApplicationStartup;
import infrastructure.workcontext.services.WorkContext;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    private final WorkContext workContext;

    public HomeServlet() {
        workContext = ApplicationStartup.getContext().getBean(WorkContext.class);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userEmail", workContext.getCurrentUser().getEmail());
        req.getRequestDispatcher("WEB-INF/jsp/home.jsp").forward(req, resp);
    }
}
