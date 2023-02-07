package service.impl;

import controller.validator.ArgumentValidator;
import exception.DaoOperationException;
import exception.TransactionException;
import model.dao.DaoFactory;
import model.dao.MovieDao;
import model.dao.PurchasedSeatDao;
import model.entity.PurchasedSeat;
import service.PurchasedSeatService;

/**
 * Implement an interface that defines different activities with purchased seat.
 */
public class PurchasedSeatServiceImpl implements PurchasedSeatService {
    private final PurchasedSeatDao purchasedSeatDao;

    public PurchasedSeatServiceImpl() {
        purchasedSeatDao = DaoFactory.getPurchasedSeatDao();
    }

    public PurchasedSeatServiceImpl(PurchasedSeatDao purchasedSeatDao) {
        this.purchasedSeatDao = purchasedSeatDao;
    }

    /**
     * Saves new purchased seat in database. Also creates ticket and updates available seats in the session
     *
     * @param seatId    - id of seat
     * @param sessionId - id of session
     * @param userId    - id of user
     * @throws DaoOperationException if there was an error executing the query
     *                               in the database
     * @see PurchasedSeat
     */
    @Override
    public synchronized void save(Long sessionId, Long seatId, Long userId) throws DaoOperationException, TransactionException {
        ArgumentValidator.checkForNull(sessionId, "An empty id value is not allowed");
        ArgumentValidator.checkForNull(seatId, "An empty id value is not allowed");
        ArgumentValidator.checkForNull(userId, "An empty id value is not allowed");
        purchasedSeatDao.save(seatId, sessionId, userId);
    }
}
