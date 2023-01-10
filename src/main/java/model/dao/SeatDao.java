package model.dao;

import exception.DaoOperationException;
import exception.ServiceException;
import model.entity.Seat;

import java.util.List;

public interface SeatDao {
    public void save(Seat seat) throws DaoOperationException;
    public void remove(Long id) throws DaoOperationException;

    public List<Seat> findAllFreeSeatForSession(Long sessionId) throws DaoOperationException;
    public Seat findById(Long id) throws DaoOperationException;
}
