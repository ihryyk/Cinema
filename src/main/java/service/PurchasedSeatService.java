package service;

import exception.DaoOperationException;
import exception.ServiceException;
import exception.TransactionException;
import model.entity.PurchasedSeat;

public interface PurchasedSeatService {
    public void save(Long sessionId, Long seatId, Long userId) throws ServiceException;
}
