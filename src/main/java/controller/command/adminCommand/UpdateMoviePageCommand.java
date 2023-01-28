package controller.command.adminCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Movie;
import org.apache.log4j.Logger;
import service.LanguageService;
import service.MovieService;
import service.ServiceFactory;

/**
 * Implementation of Command interface that perform displaying update movie page.
 */
public class UpdateMoviePageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(UpdateMoviePageCommand.class);
    private final LanguageService languageService = ServiceFactory.getLanguageService();
    private final MovieService movieService = ServiceFactory.getMovieService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException {
        request.setAttribute("languages",languageService.findAll());
        Movie movie = movieService.findById(Long.valueOf(request.getParameter("movieId")));
        request.setAttribute("oldPoster", movie.getPoster());
        request.setAttribute("movie", movie);
        logger.info("Update movie page command");
        return "updateMovie";
    }
}
