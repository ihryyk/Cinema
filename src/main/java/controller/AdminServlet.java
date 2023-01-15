package controller;

import exception.ServiceException;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/cinema/admin/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
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
                        Movie movie = movieService.findById(Long.valueOf(request.getParameter("movieId")));
                        request.setAttribute("oldPoster", movie.getPoster());
                        request.setAttribute("movie", movie);
                        request.getRequestDispatcher("/views/updateMovie.jsp").forward(request, response);
                        break;
                    case ("/session"):
                        Long movieId = Long.valueOf(request.getParameter("movieId"));
                        request.setAttribute("movieId",movieId);
                        request.setAttribute("sessions", sessionService.findByMovieId(movieId, 1L));
                        request.setAttribute("numberSpectators", seatService.countOccupiedSeatsInTheAllSession(movieId));
                        request.getRequestDispatcher("/views/adminSession.jsp").forward(request, response);
                        break;
                    case ("/addSession"):
                        addSessionDoGet(request,response);
                        request.getRequestDispatcher("/views/addSession.jsp").forward(request, response);
                        break;
                    case ("/updateSession"):
                        updateSessionGet(request,response);
                        request.getRequestDispatcher("/views/updateSession.jsp").forward(request, response);
                    case ("/seat"):
                        getSeats(request);
                        request.getRequestDispatcher("/views/adminSeat.jsp").forward(request, response);
                }
            }
        }catch (ServiceException e){
            response.sendRedirect("/cinema/error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            switch (request.getPathInfo()) {
                case ("/addMovie"):
                    saveMovie(request);
                    response.sendRedirect("/cinema/admin");
                    break;
                case ("/updateMovie"):
                    updateMovie(request);
                    response.sendRedirect("/cinema/admin");
                    break;
                case ("/addSession"):
                    addSession(request);
                    response.sendRedirect("/cinema/admin");
                    break;
                case ("/updateSession"):
                    updateSession(request,response);
                    response.sendRedirect("/cinema/admin");
                case("/cancelMovie"):
                    cancelMovie(request,response);
                    response.sendRedirect("/cinema/admin");
            }
        }catch (ServiceException | ServletException| ParseException e){
            response.sendRedirect("/cinema/error");
        }
    }

    private void cancelMovie(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        movieService.delete(movieId);
    }

    private void getSeats(HttpServletRequest request) throws ServiceException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        List<Seat> freeSeats = seatService.findAllFreeSeatForSession(sessionId);
        List<Seat> busySeats = seatService.findAllBusySeatForSession(sessionId);
        request.setAttribute("freeSeats", freeSeats);
        request.setAttribute("busySeats", busySeats);
        request.setAttribute("session",sessionService.findById(sessionId,1L));
    }

    private void updateSession(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Session session = getSessionFromJsp(request);
        session.setId(Long.valueOf(request.getParameter("sessionId")));
        sessionService.update(session);
    }

    private void updateSessionGet(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long movieId = Long.valueOf(req.getParameter("movieId"));
        req.setAttribute("formats", Arrays.asList(MovieFormat.values()));
        req.setAttribute("movieId", movieId);

        req.setAttribute("session", sessionService.findById(Long.valueOf(req.getParameter("sessionId")),1L));
        req.getSession().setAttribute("popUpsSuccess", "Session successfully updated");
    }

    private void addSessionDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long movieId = Long.valueOf(req.getParameter("movieId"));
        req.setAttribute("movieId", movieId);
        req.setAttribute("formats", Arrays.asList(MovieFormat.values()));
    }

    private void addSession(HttpServletRequest request) throws ServiceException {
        Session session = getSessionFromJsp(request);
        sessionService.save(session);
        request.getSession().setAttribute("popUpsSuccess", "Session successfully added");
    }

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

    private void updateMovie(HttpServletRequest request) throws ServletException, ServiceException, IOException {
        Movie movie = getMovieFromJsp(request);
        movie.setId(Long.valueOf(request.getParameter("movieId")));
        if (movie.getPoster().available()==0){
            movie.setPoster(movieService.getPosterByMovieId(movie.getId()));
        }
        movieService.update(movie);
    }

    private void saveMovie (HttpServletRequest request) throws ServletException, IOException, ServiceException, ParseException {
        movieService.save(getMovieFromJsp(request));
    }

    private Movie getMovieFromJsp (HttpServletRequest request) throws ServiceException, ServletException, IOException {
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
