package service;

import exception.DaoOperationException;
import exception.TransactionException;
import model.entity.PurchasedSeat;

/**
 * The interface defines methods for implementing different
 * activities with purchased seat
 *
 */
public interface PurchasedSeatService {

    /**
     * Saves new purchased seat in database. Also creates ticket and updates available seats in the session
     * @param seatId - id of seat
     * @param sessionId - id of session
     * @param userId - id of user
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see PurchasedSeat
     */
    public void save(Long sessionId, Long seatId, Long userId) throws DaoOperationException, TransactionException;
}
