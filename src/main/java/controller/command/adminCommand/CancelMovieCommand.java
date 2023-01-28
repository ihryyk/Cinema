package controller.command.adminCommand;

import controller.command.ICommand;
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
 *
 *Implementation of a command interface that performs movie cancellation
 */
public class CancelMovieCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(CancelMovieCommand.class);
    private final MovieService movieService = ServiceFactory.getMovieService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        movieService.delete(movieId);
        logger.info("Cancel movie command");
        return "cinema?command=ADMIN_PAGE";
    }
}
