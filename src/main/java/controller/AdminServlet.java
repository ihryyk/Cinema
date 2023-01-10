package controller;

import exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Language;
import model.entity.Movie;
import model.entity.MovieDescription;
import service.LanguageService;
import service.MovieService;
import service.ServiceFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            switch (req.getPathInfo()) {
                case ("/saveMovie"):
                    req.setAttribute("languages",languageService.findAll());
                    req.getRequestDispatcher("/views/addMovie.jsp").forward(req, resp);
                    break;
            }
        } catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            switch (request.getPathInfo()) {
                case ("/saveMovie"):
                    saveMovie(request);
                    response.sendRedirect("/cinema");
                    break;
            }
        }catch (ServiceException | ServletException| ParseException e){
            throw new RuntimeException(e);
        }
    }

    private void saveMovie (HttpServletRequest request) throws ServletException, IOException, ServiceException, ParseException {
        Movie movie = new Movie();
        movie.setPoster(request.getPart("poster").getInputStream());
        movie.setOriginalName(request.getParameter("originalName"));
        movie.setReleaseDate(Timestamp.valueOf(request.getParameter("releaseDate").replace("T", " ") + ":00"));
        movie.setMovieDescriptionList(getMovieDescriptionFromJsp(request));
        movieService.save(movie);
    }
    private List<MovieDescription> getMovieDescriptionFromJsp(HttpServletRequest request) throws ServiceException {
        List<MovieDescription> descriptions = new ArrayList<>();
        for (Language language : languageService.findAll()) {
            descriptions.add(new MovieDescription(request.getParameter(language.getName()+"Title"),request.getParameter(language.getName()+"Director"),language));
        }
        return descriptions;
    }


}
