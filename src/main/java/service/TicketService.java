package service;

import exception.DaoOperationException;
import model.entity.Ticket;

import java.util.List;
/**
 * The interface defines methods for implementing different
 * activities with ticket
 *
 */
public interface TicketService {

    /**
     * Returns list os user's tickets.
     * @param userId - id of user
     * @param languageId - id of language
     * @return list of user's ticket
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Ticket
     */
    public List<Ticket> findByUserId(Long userId, Long languageId) throws DaoOperationException;
}
