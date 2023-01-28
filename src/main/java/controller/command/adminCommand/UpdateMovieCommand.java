package controller.command.adminCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Language;
import model.entity.Movie;
import model.entity.MovieDescription;
import org.apache.log4j.Logger;
import service.LanguageService;
import service.MovieService;
import service.ServiceFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *Implementation of a command interface that performs movie updates
 */
public class UpdateMovieCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(UpdateMovieCommand.class);
    private final LanguageService languageService = ServiceFactory.getLanguageService();
    private final MovieService movieService = ServiceFactory.getMovieService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        Movie movie = getMovieFromJsp(request);
        movie.setId(Long.valueOf(request.getParameter("movieId")));
        request.getSession().setAttribute("popUpsSuccess", "Movie successfully updated");
        movieService.update(movie);
        logger.info("Update movie command");
        return "cinema?command=ADMIN_PAGE";
    }

    /**
     * Get information about movie from request.
     *
     * @param request  {@link HttpServletRequest}.
     * @return movie which contains the information of movie from request.
     */
    private Movie getMovieFromJsp (HttpServletRequest request) throws ServletException, IOException, DaoOperationException {
        Movie movie = new Movie();
        movie.setOriginalName(request.getParameter("originalName"));
        movie.setPoster(request.getPart("poster").getInputStream());
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
