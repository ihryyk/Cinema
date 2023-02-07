package controller.command.adminCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import service.SeatService;
import service.ServiceFactory;
import service.SessionService;

/**
 * Implementation of Command interface that perform displaying admin seats page.
 */
public class AdminSeatsPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(AdminSeatsPageCommand.class);
    private final SeatService seatService = ServiceFactory.getSeatService();
    private final SessionService sessionService =ServiceFactory.getSessionService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        request.setAttribute("seats", seatService.findAllBySession(sessionId));
        request.setAttribute("session",sessionService.findByIdAndLanguageId(sessionId,(Long) request.getSession().getAttribute("dbLanguage")));
        logger.info("Admin seats page command");
        return  "adminSeat";
    }
}
