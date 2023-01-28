package controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The filter checks access to a jsp page.
 *
 * @see Filter
 */
@WebFilter("/cinema")
public class AccessToJsp implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(req.getServletPath()!=null && req.getServletPath().endsWith(".jsp")) {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.sendRedirect("/cinema?command=ERROR_PAGE");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
