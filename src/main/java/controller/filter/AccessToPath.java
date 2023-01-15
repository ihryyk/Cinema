package controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;

import java.io.IOException;

@WebFilter({"/cinema/user/*", "/cinema/admin/*"})
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

            if (user!= null) {
                switch (user.getRole().name()) {
                    case ("ADMIN"):
                        if (!req.getServletPath().equals("/cinema/admin") || req.getServletPath().equals("")) {
                            resp.sendRedirect("/cinema/error");
                            return;
                        }
                        break;
                    case ("USER"):
                        if (!req.getServletPath().equals("/cinema/user") || req.getServletPath().equals("")) {
                            resp.sendRedirect("/cinema/error");
                            return;
                        }
                        break;
                }
            } else {
                if (req.getServletPath().equals("/cinema/user") || req.getServletPath().equals("/cinema/admin")) {
                    resp.sendRedirect("/cinema/error");
                    return;
                }
                filterChain.doFilter(servletRequest, servletResponse);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
}
