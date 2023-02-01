package controller.command.adminCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.impl.TicketDaoImpl;
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
 * Implementation of a command interface that performs the addition of a movie
 */
public class AddMovieCommand implements ICommand {
    private final MovieService movieService = ServiceFactory.getMovieService();
    private final LanguageService languageService = ServiceFactory.getLanguageService();

    private final static Logger logger = Logger.getLogger(AddMovieCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        movieService.save(getMovieFromJsp(request));
        request.getSession().setAttribute("popUpsSuccess", "AddedMovie");
        logger.info("Add movie command");
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
