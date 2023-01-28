package controller.filter;

import controller.command.CommandEnum;
import controller.command.CommandFactory;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;

import java.io.IOException;

/**
 * The filter checks user access to a specific command.
 *
 * @see Filter
 */
@WebFilter("/cinema")
public class AccessToPath implements Filter {

    /**
     * The method checks user access to a specific url-pattern.
     *
     * @param servletRequest  {@link ServletRequest}.
     * @param servletResponse {@link ServletResponse}.
     * @param filterChain     {@link FilterChain}
     * @throws ServletException if any inner exception in servlet occurs
     * @throws IOException      if I/O error occurs.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        User user = (User) req.getSession().getAttribute("user");
        String command = req.getParameter("command");
        if (user!= null) {
            switch (user.getRole().name()) {
                case ("ADMIN"):
                    if (CommandEnum.getUserCommand().contains(command)) {
                        resp.sendRedirect("/cinema?command=ERROR_PAGE");
                        return;
                    }
                    break;
                case ("USER"):
                    if (CommandEnum.getAdminCommand().contains(command)) {
                        resp.sendRedirect("/cinema?command=ERROR_PAGE");
                        return;
                    }
                    break;
            }
        } else {
            if (CommandEnum.getUserCommand().contains(command) || CommandEnum.getAdminCommand().contains(command)) {
                resp.sendRedirect("/cinema?command=ERROR_PAGE");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
