package controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class AccessToJsp implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(req.getServletPath()!=null && req.getServletPath().endsWith(".jsp")) {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.sendRedirect("/cinema/error");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
