package controller.command.userCommand;

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
 * Implementation of the command interface, which performs the formation of the ticket
 */
public class TicketFormationCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(TicketFormationCommand.class);
    private final SeatService seatService = ServiceFactory.getSeatService();
    private final SessionService sessionService = ServiceFactory.getSessionService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        logger.info("Ticket formation command");
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        Long seatId = Long.valueOf(request.getParameter("seatId"));
        if (seatService.ifSeatExist(seatId,sessionId)){
            request.setAttribute("session",sessionService.findByIdAndLanguageId(sessionId,(Long) request.getSession().getAttribute("dbLanguage")));
            request.setAttribute("seat", seatService.findById(seatId));
            return "ticket";
        }else {
            request.getSession().setAttribute("popUpsError","sorry, but this seat is already taken");
            return "cinema?command=INDEX_PAGE";
        }
    }
}
