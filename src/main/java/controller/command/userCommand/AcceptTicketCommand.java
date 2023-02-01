package controller.command.userCommand;

import controller.command.ICommand;
import controller.command.unregisnterUserCommand.SeatsPageCommand;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;
import org.apache.log4j.Logger;
import service.PurchasedSeatService;
import service.SeatService;
import service.ServiceFactory;

import java.io.IOException;

/**
 * Implementation of Command interface that perform purchase of a ticket
 */
public class AcceptTicketCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(AcceptTicketCommand.class);
    private final SeatService seatService = ServiceFactory.getSeatService();
    private final PurchasedSeatService purchasedSeatService = ServiceFactory.getPurchasedSeatService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        logger.info("AcceptTickets Command");
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        Long seatId = Long.valueOf(request.getParameter("seatId"));
        if (seatService.ifSeatExist(seatId,sessionId)) {
            User user = (User) request.getSession().getAttribute("user");
            purchasedSeatService.save(seatId,sessionId,user.getId());
            request.getSession().setAttribute("popUpsSuccess","AcceptTicket");
        }else {
            request.getSession().setAttribute("popUpsError","BusySeat");
        }
        return "cinema?command=INDEX_PAGE";
    }
}
