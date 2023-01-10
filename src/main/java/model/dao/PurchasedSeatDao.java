package model.dao;

import exception.DaoOperationException;
import exception.TransactionException;
import model.entity.PurchasedSeat;
import model.entity.User;

public interface PurchasedSeatDao {
    public void save(Long sessionId, Long seatId, Long userId) throws DaoOperationException, TransactionException;
}
