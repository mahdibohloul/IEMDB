package framework.interceptors.filters;


import domain.user.exceptions.UserNotFoundException;
import infrastructure.startup.ApplicationStartup;
import infrastructure.workcontext.services.WorkContext;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@WebFilter(urlPatterns = "/*")
public class WorkContextFilter implements Filter {
    private final WorkContext workContext;

    public WorkContextFilter() {
        this.workContext = ApplicationStartup.getContext().getBean(WorkContext.class);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getRequestURI().equals("/login")) {
            if (request.getSession().getAttribute("userEmail") != null) {
                try {
                    workContext.setCurrentUser(request.getSession().getAttribute("userEmail").toString());
                    response.sendRedirect("/");
                } catch (UserNotFoundException e) {
                    request.getSession().removeAttribute("userEmail");
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else if (request.getSession().getAttribute("userEmail") == null) {
            response.sendRedirect("/login");
        } else {
            String userEmail = request.getSession().getAttribute("userEmail").toString();
            try {
                workContext.setCurrentUser(userEmail);
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (UserNotFoundException e) {
                response.sendRedirect("/login");
            }
        }
//        filterChain.doFilter(servletRequest, servletResponse);
    }
}
