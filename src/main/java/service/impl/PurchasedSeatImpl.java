package service.impl;

import exception.DaoOperationException;
import exception.ServiceException;
import exception.TransactionException;
import model.dao.DaoFactory;
import model.dao.MovieDao;
import model.dao.PurchasedSeatDao;
import model.entity.PurchasedSeat;
import service.PurchasedSeatService;

public class PurchasedSeatImpl implements PurchasedSeatService {
    private final PurchasedSeatDao purchasedSeatDao = DaoFactory.getPurchasedSeatDao();
    @Override
    public void save(Long sessionId, Long seatId, Long userId) throws ServiceException {
        try {
            purchasedSeatDao.save(sessionId,seatId,userId);
        } catch (DaoOperationException | TransactionException e) {
            throw new ServiceException("message",e);
        }
    }
}
