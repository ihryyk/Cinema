package service;

import exception.DaoOperationException;
import exception.ServiceException;
import model.entity.Seat;

import java.util.List;

public interface SeatService {
    public void save(Seat seat) throws ServiceException;
    public void remove(Long id) throws ServiceException;
    public List<Seat> findAllFreeSeatForSession(Long sessionId) throws ServiceException;
    public Seat findById(Long id) throws ServiceException;
}
