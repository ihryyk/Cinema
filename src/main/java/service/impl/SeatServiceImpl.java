package service.impl;

import exception.DaoOperationException;
import exception.ServiceException;
import model.dao.DaoFactory;
import model.dao.SeatDao;
import model.entity.Seat;
import service.SeatService;

import java.util.List;

public class SeatServiceImpl implements SeatService {
   SeatDao seatDao = DaoFactory.getSeatDao();

    @Override
    public void save(Seat seat) throws ServiceException {

    }

    @Override
    public void remove(Long id) throws ServiceException {

    }

    @Override
    public List<Seat> findAllFreeSeatForSession(Long sessionId) throws ServiceException {
        try {
           return seatDao.findAllFreeSeatForSession(sessionId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public Seat findById(Long id) throws ServiceException {
        try {
            return seatDao.findById(id);
        } catch (DaoOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Seat> findAllBusySeatForSession(Long sessionId) throws ServiceException {
        try {
            return seatDao.findAllBusySeatForSession(sessionId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public Long countOccupiedSeatsInTheAllSession(Long sessionId) throws ServiceException {
        try {
            return seatDao.countOccupiedSeatsInTheSession(sessionId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }
}
