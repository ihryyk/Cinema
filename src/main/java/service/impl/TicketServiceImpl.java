package service.impl;

import controller.validator.ArgumentValidator;
import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.TicketDao;
import model.entity.Ticket;
import service.TicketService;

import java.util.List;

/**
 * Implement an interface that defines different activities with ticket.
 *
 */
public class TicketServiceImpl implements TicketService {
    private final TicketDao ticketDao;

    public TicketServiceImpl(){
        ticketDao = DaoFactory.getTicketDao();
    }

    public TicketServiceImpl(TicketDao ticketDao){
        this.ticketDao = ticketDao;
    }

    /**
     * Returns list os user's tickets.
     * @param userId - id of user
     * @param languageId - id of language
     * @return list of user's ticket
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Ticket
     */
    @Override
    public List<Ticket> findByUserId(Long userId, Long languageId) throws DaoOperationException {
        ArgumentValidator.checkForNull(userId,"An empty id value is not allowed");
        ArgumentValidator.checkForNull(languageId,"An empty id value is not allowed");
        return ticketDao.findByUser(userId, languageId);
    }
}
