package controller.command.adminCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Movie;
import model.entity.Session;
import model.enums.MovieFormat;
import org.apache.log4j.Logger;
import service.ServiceFactory;
import service.SessionService;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 *
 * Implementation of a command interface that performs the addition of a session
 */
public class AddSessionCommand implements ICommand {
    private final static Logger logger = Logger.getLogger(AddSessionCommand.class);
    private final SessionService sessionService = ServiceFactory.getSessionService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        Session session = getSessionFromJsp(request);
        sessionService.save(session);
        request.getSession().setAttribute("popUpsSuccess", "AddedSession");
        logger.info("Add session command");
        return "cinema?command=ADMIN_PAGE";
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
        return session;
    }
}
