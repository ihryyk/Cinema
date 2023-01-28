package controller.command.unregisnterUserCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import service.SeatService;
import service.ServiceFactory;
import service.SessionService;

import java.io.IOException;

/**
 * Implementation of Command interface that perform displaying seats page.
 */
public class SeatsPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(SeatsPageCommand.class);
    private final SeatService seatService = ServiceFactory.getSeatService();
    private final SessionService sessionService = ServiceFactory.getSessionService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        request.setAttribute("seats", seatService.findSeatsSession(sessionId));
        request.setAttribute("session",sessionService.findByIdAndLanguageId(sessionId,(Long) request.getSession().getAttribute("dbLanguage")));
        logger.info("Seats page command");
        return "seat";
    }
}
