package controller;

import controller.util.StartPosition;
import exception.DaoOperationException;
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
/**
 * Functions that are on the main page.
 */
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
                        getSeats(request, response);
                        break;
                    case ("/movie/session"):
                        showMovieSession(request, response);
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
        } catch (DaoOperationException e) {
            response.sendRedirect("/cinema/error");
        }
    }

    private void getTodayMovie(HttpServletRequest request) {

    }

    /**
     * Adds information about seats in the request.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     * @throws ServletException      if Servlet error occurs
     */
    private void getSeats(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        List<Seat> seats = seatService.findAllFreeSeatForSession(sessionId);
        request.setAttribute("seats", seats);
        request.setAttribute("session",sessionService.findByIdAndLanguageId(sessionId,1L));
        request.getRequestDispatcher("/views/seat.jsp").forward(request, response);
    }

    /**
     * Adds information about session in the request.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     * @throws ServletException      if Servlet error occurs
     */
    private void showMovieSession(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        if (request.getParameter("groupBy")!=null){
            request.setAttribute("sessions", sessionService.sortBy(request.getParameter("groupBy"),1L,movieId));
        }else {
            request.setAttribute("sessions", sessionService.findByMovieId(movieId, 1L));
        }
        request.setAttribute("movieId",movieId);
        request.getRequestDispatcher("/views/session.jsp").forward(request, response);
    }
}
