package controller;

import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.*;
import model.enums.MovieFormat;
import service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Functions which are used by admin.
 *
 */
@WebServlet("/cinema/admin/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AdminServlet extends HttpServlet {
    private final LanguageService languageService = ServiceFactory.getLanguageService();
    private final MovieService movieService = ServiceFactory.getMovieService();
    private final SessionService sessionService =ServiceFactory.getSessionService();
    private final SeatService seatService = ServiceFactory.getSeatService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            if (request.getPathInfo()==null){
                if (request.getParameter("movieName")!=null){
                    request.setAttribute("movies", movieService.findByLanguageAndTitle(1L,request.getParameter("movieName")));
                }else
                    request.setAttribute("movies", movieService.findAllByLanguage(1L));
                request.getRequestDispatcher("/views/adminIndex.jsp").forward(request, response);
            }else {
                switch (request.getPathInfo()) {
                    case ("/addMovie"):
                        request.setAttribute("languages",languageService.findAll());
                        request.getRequestDispatcher("/views/addMovie.jsp").forward(request, response);
                        break;
                    case ("/updateMovie"):
                        updateMovieGet(request, response);
                        break;
                    case ("/session"):
                        getSession(request, response);
                        break;
                    case ("/addSession"):
                        addSessionDoGet(request,response);
                        break;
                    case ("/updateSession"):
                        updateSessionGet(request,response);
                    case ("/seat"):
                        getSeats(request, response);
                }
            }
        }catch (DaoOperationException e){
            response.sendRedirect("/cinema/error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            switch (request.getPathInfo()) {
                case ("/addMovie"):
                    saveMovie(request, response);
                    break;
                case ("/updateMovie"):
                    updateMovie(request, response);
                    break;
                case ("/addSession"):
                    addSession(request, response);
                    break;
                case ("/updateSession"):
                    updateSession(request,response);
                    break;
                case ("/cancelMovie"):
                    cancelMovie(request,response);
                    break;
            }
        }catch (ServletException|DaoOperationException | TransactionException e){
            response.sendRedirect("/cinema/error");
        }
    }

    /**
     * Adds information about movie in the request.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     * @throws ServletException      if Servlet error occurs.
     */
    private void updateMovieGet(HttpServletRequest request ,HttpServletResponse response) throws DaoOperationException, ServletException, IOException {
        request.setAttribute("languages",languageService.findAll());
        Movie movie = movieService.findById(Long.valueOf(request.getParameter("movieId")));
        request.setAttribute("oldPoster", movie.getPoster());
        request.setAttribute("movie", movie);
        request.getRequestDispatcher("/views/updateMovie.jsp").forward(request, response);
    }

    /**
     * Adds information about session in the request.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     * @throws ServletException      if Servlet error occurs.
     */
    private void getSession(HttpServletRequest request ,HttpServletResponse response) throws DaoOperationException, ServletException, IOException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        request.setAttribute("movieId",movieId);
        request.setAttribute("sessions", sessionService.findByMovieId(movieId, 1L));
        request.setAttribute("numberSpectators", seatService.countOccupiedSeatsInTheAllSession(movieId));
        request.getRequestDispatcher("/views/adminSession.jsp").forward(request, response);
    }

    /**
     * Sets the movie's delete status to 'true'.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     */
    private void cancelMovie(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, IOException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        movieService.delete(movieId);
        response.sendRedirect("/cinema/admin");
    }

    /**
     * Adds information about seats in the request.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     * @throws ServletException      if Servlet error occurs.
     */
    private void getSeats(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        List<Seat> freeSeats = seatService.findAllFreeSeatForSession(sessionId);
        List<Seat> busySeats = seatService.findAllBusySeatForSession(sessionId);
        request.setAttribute("freeSeats", freeSeats);
        request.setAttribute("busySeats", busySeats);
        request.setAttribute("session",sessionService.findByIdAndLanguageId(sessionId,1L));
        request.getRequestDispatcher("/views/adminSeat.jsp").forward(request, response);
    }

    /**
     * Updates session information.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     */
    private void updateSession(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, IOException {
        Session session = getSessionFromJsp(request);
        session.setId(Long.valueOf(request.getParameter("sessionId")));
        sessionService.update(session);
        request.getSession().setAttribute("popUpsSuccess", "Session successfully updated");
        response.sendRedirect("/cinema/admin");
    }

    /**
     * Adds information about session in the request.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     * @throws ServletException      if Servlet error occurs.
     */
    private void updateSessionGet(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        request.setAttribute("formats", Arrays.asList(MovieFormat.values()));
        request.setAttribute("movieId", movieId);
        request.setAttribute("session", sessionService.findByIdAndLanguageId(Long.valueOf(request.getParameter("sessionId")),1L));
        request.getRequestDispatcher("/views/updateSession.jsp").forward(request, response);
    }

    /**
     * Adds information about session in the request.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws IOException           if I/O error occurs.
     * @throws ServletException      if Servlet error occurs.
     */
    private void addSessionDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        request.setAttribute("movieId", movieId);
        request.setAttribute("formats", Arrays.asList(MovieFormat.values()));
        request.getRequestDispatcher("/views/addSession.jsp").forward(request, response);
    }

    /**
     * Save new session.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     */
    private void addSession(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, IOException {
        Session session = getSessionFromJsp(request);
        sessionService.save(session);
        request.getSession().setAttribute("popUpsSuccess", "Session successfully added");
        response.sendRedirect("/cinema/admin");
    }

    /**
     * Get information about session from request.
     *
     * @param request  {@link HttpServletRequest}.
     * @return session which contains the information of session from request.
     */
    private Session getSessionFromJsp(HttpServletRequest request){
        Session session = new Session();
        Movie movie = new Movie();
        movie.setId(Long.valueOf(request.getParameter("movieId")));
        session.setPrice(BigDecimal.valueOf(Long.parseLong(request.getParameter("price"))));
        session.setMovie(movie);
        session.setEndTime(Timestamp.valueOf(request.getParameter("endTime").replace("T", " ") + ":00"));
        session.setStartTime(Timestamp.valueOf(request.getParameter("startTime").replace("T", " ") + ":00"));
        session.setFormat(MovieFormat.valueOf(request.getParameter("format")));
        session.setAvailableSeats(Integer.parseInt(request.getParameter("availableSeats")));
        return session;
    }

    /**
     * Update movie information.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                          in the DAO
     * @throws IOException      if I/O error occurs.
     * @throws ServletException  if Servlet error occurs.
     * @throws TransactionException if there was an error executing the transaction
     *                              in the DAO
     */
    private void updateMovie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoOperationException, TransactionException {
        Movie movie = getMovieFromJsp(request);
        movie.setId(Long.valueOf(request.getParameter("movieId")));
        if (movie.getPoster().available()==0){
            movie.setPoster(movieService.getPosterByMovieId(movie.getId()));
        }
        movieService.update(movie);
        response.sendRedirect("/cinema/admin");
    }

    /**
     * Save movie.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                          in the DAO
     * @throws IOException      if I/O error occurs.
     * @throws ServletException  if Servlet error occurs
     */
    private void saveMovie (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoOperationException, TransactionException {
        movieService.save(getMovieFromJsp(request));
        response.sendRedirect("/cinema/admin");
    }

    /**
     * Get information about movie from request.
     *
     * @param request  {@link HttpServletRequest}.
     * @return movie which contains the information of movie from request.
     */
    private Movie getMovieFromJsp (HttpServletRequest request) throws ServletException, IOException, DaoOperationException {
        Movie movie = new Movie();
        movie.setPoster(request.getPart("poster").getInputStream());
        movie.setOriginalName(request.getParameter("originalName"));
        movie.setReleaseDate(Timestamp.valueOf(request.getParameter("releaseDate").replace("T", " ") + ":00"));
        movie.setAvailableAge(Short.parseShort(request.getParameter("availableAge")));
        List<MovieDescription> descriptions = new ArrayList<>();
        for (Language language : languageService.findAll()) {
            descriptions.add(new MovieDescription(request.getParameter(language.getName()+"Title"),request.getParameter(language.getName()+"Director"),language));
        }
        movie.setMovieDescriptionList(descriptions);
        return movie;
    }

}
