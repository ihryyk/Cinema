package controller.command.userCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;
import org.apache.log4j.Logger;
import service.ServiceFactory;
import service.TicketService;

import java.io.IOException;

/**
 * Implementation of Command interface that perform displaying tickets page.
 */
public class TicketsPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(TicketsPageCommand.class);
    private final TicketService ticketService = ServiceFactory.getTicketService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        logger.info("Tickets page command");
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("tickets", ticketService.findByUserId(user.getId(),(Long) request.getSession().getAttribute("dbLanguage")) );
        return "userTickets";
    }
}
