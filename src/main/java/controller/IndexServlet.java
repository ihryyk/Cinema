package controller;

import controller.util.StartPosition;
import exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Seat;
import service.MovieService;
import service.SeatService;
import service.ServiceFactory;
import service.SessionService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/cinema/*")
public class IndexServlet extends HttpServlet {
    private final MovieService movieService = ServiceFactory.getMovieService();
    private final SeatService seatService = ServiceFactory.getSeatService();
    private final SessionService sessionService = ServiceFactory.getSessionService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getPathInfo()!=null){
                switch (request.getPathInfo()){
                    case ("/movie/session/seat"):
                        getSeats(request);
                        request.getRequestDispatcher("/views/seat.jsp").forward(request, response);
                        break;
                    case ("/movie/session"):
                        showMovieSession(request);
                        request.getRequestDispatcher("/views/session.jsp").forward(request, response);
                        break;
                    case ("/movie/showToday"):
                        getTodayMovie(request);
                }
            }
            if (request.getParameter("movieName")!=null){
                int start = StartPosition.getStartPosition(request);
                request.setAttribute("count", movieService.getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId(request.getParameter("movieName"),1L));
                request.setAttribute("movies", movieService.findAllWhichHaveSessionInTheFutureByLanguageAndTitle(1L,request.getParameter("movieName"),start,StartPosition.AMOUNT_MOVIE_ON_A_PAGE));
                request.setAttribute("movieName", request.getParameter("movieName"));
            }else{
                int start = StartPosition.getStartPosition(request);
                request.setAttribute("count", movieService.getCountMovieWhichHaveSessionInTheFuture());
                request.setAttribute("movies", movieService.findAllWhichHaveSessionInTheFutureByLanguage(1L,start,StartPosition.AMOUNT_MOVIE_ON_A_PAGE));
            }
            request.getRequestDispatcher("/views/index.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    private void getTodayMovie(HttpServletRequest request) {

    }

    private void getSeats(HttpServletRequest request) throws ServiceException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        List<Seat> seats = seatService.findAllFreeSeatForSession(sessionId);
        request.setAttribute("seats", seats);
        request.setAttribute("session",sessionService.findById(sessionId,1L));
    }
    private void showMovieSession(HttpServletRequest req) throws ServiceException {
        Long movieId = Long.valueOf(req.getParameter("movieId"));
        if (req.getParameter("groupBy")!=null){
            req.setAttribute("sessions", sessionService.sortBy(req.getParameter("groupBy"),1L,movieId));
        }else {
            req.setAttribute("sessions", sessionService.findByMovieId(movieId, 1L));
        }
        req.setAttribute("movieId",movieId);
    }
}
