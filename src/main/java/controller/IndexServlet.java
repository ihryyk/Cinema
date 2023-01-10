package controller;

import exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MovieService;
import service.ServiceFactory;

import java.io.IOException;

@WebServlet("/cinema")
public class IndexServlet extends HttpServlet {
    private final MovieService movieService = ServiceFactory.getMovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            if (req.getParameter("movieName")!=null){
                req.setAttribute("movies", movieService.findByLanguageAndTitle(1L,req.getParameter("movieName")));
            }else
                req.setAttribute("movies", movieService.findAllByLanguage(1L));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }
}
