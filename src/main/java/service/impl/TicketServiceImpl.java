package service.impl;

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
    private final TicketDao ticketDao = DaoFactory.getTicketDao();

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
            return ticketDao.findByUserId(userId, languageId);
    }
}
