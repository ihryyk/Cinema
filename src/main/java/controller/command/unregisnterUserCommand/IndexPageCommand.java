package controller.command.unregisnterUserCommand;

import controller.command.ICommand;
import controller.util.Localization;
import controller.util.StartPosition;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import service.MovieService;
import service.ServiceFactory;

import java.io.IOException;

/**
 * Implementation of Command interface that perform displaying index page.
 */
public class IndexPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(IndexPageCommand.class);
    private final MovieService movieService = ServiceFactory.getMovieService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        Localization.addLocalization(request);
        if (request.getParameter("movieName")!=null){
            int start = StartPosition.getStartPosition(request);
            request.setAttribute("count", movieService.getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId(request.getParameter("movieName"),1L));
            request.setAttribute("movies", movieService.findAllWhichHaveSessionInTheFutureByLanguageAndTitle((Long) request.getSession().getAttribute("dbLanguage"),request.getParameter("movieName"),start,StartPosition.AMOUNT_MOVIE_ON_A_PAGE));
            request.setAttribute("movieName", request.getParameter("movieName"));
        }else if (request.getParameter("todayMovies")!=null){
            int start = StartPosition.getStartPosition(request);
            request.setAttribute("count", movieService.getCountMovieWhichHaveSessionToday());
            request.setAttribute("movies", movieService.findAllWhichHaveSessionToday((Long) request.getSession().getAttribute("dbLanguage"),start,StartPosition.AMOUNT_MOVIE_ON_A_PAGE));
            request.setAttribute("todayMovies", true);
        }else {
            int start = StartPosition.getStartPosition(request);
            request.setAttribute("count", movieService.getCountMovieWhichHaveSessionInTheFuture());
            request.setAttribute("movies", movieService.findAllWhichHaveSessionInTheFutureByLanguage((Long) request.getSession().getAttribute("dbLanguage"),start,StartPosition.AMOUNT_MOVIE_ON_A_PAGE));
        }
        logger.info("Index page command");
        return "index";
    }
}
