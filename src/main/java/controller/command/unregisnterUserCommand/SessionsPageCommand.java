package controller.command.unregisnterUserCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Session;
import org.apache.log4j.Logger;
import service.MovieService;
import service.ServiceFactory;
import service.SessionService;

import java.io.IOException;

/**
 * Implementation of Command interface that perform displaying sessions page.
 */
public class SessionsPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(SessionsPageCommand.class);
    private final SessionService sessionService = ServiceFactory.getSessionService();
    private final MovieService movieService = ServiceFactory.getMovieService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        if (request.getParameter("groupBy")!=null){
            request.setAttribute("sessions", sessionService.sortBy(request.getParameter("groupBy"),movieId));
        }else {
            request.setAttribute("sessions", sessionService.findByMovieId(movieId));
        }
        request.setAttribute("movie",movieService.findByIdAndLanguageId(movieId,(Long) request.getSession().getAttribute("dbLanguage")));
        request.setAttribute("movieId",movieId);
        logger.info("Session page command");
        return "session";
    }
}
