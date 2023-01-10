package controller;

import exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ServiceFactory;
import service.SessionService;
import service.TicketService;

import java.io.IOException;

@WebServlet("/cinema/movie/session")
public class SessionServlet extends HttpServlet {
    private final SessionService sessionService = ServiceFactory.getSessionService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long movieId = Long.valueOf(req.getParameter("movieId"));
        try {
            if (req.getParameter("groupBy")!=null){
                req.setAttribute("sessions", sessionService.sortBy(req.getParameter("groupBy"),1L,movieId));
            }else {
                req.setAttribute("sessions", sessionService.findByMovieId(movieId, 1L));
            }

            req.setAttribute("movieId",movieId);
            req.getRequestDispatcher("/views/session.jsp").forward(req, resp);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
}
