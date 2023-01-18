package service.impl;

import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.SeatDao;
import model.entity.Movie;
import model.entity.Seat;
import service.SeatService;

import java.util.List;

/**
 * Implement an interface that defines different activities with seat.
 *
 */
public class SeatServiceImpl implements SeatService {
   SeatDao seatDao = DaoFactory.getSeatDao();

    /**
     * Returns list of free seat in the session from database
     * @param sessionId - id of session
     * @return list of seat
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Seat
     */
    @Override
    public List<Seat> findAllFreeSeatForSession(Long sessionId) throws DaoOperationException {
           return seatDao.findAllFreeSeatForSession(sessionId);
    }

    /**
     * Returns seat by id
     * @param id - id of seat
     * @return seat
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Seat
     */
    @Override
    public Seat findById(Long id) throws DaoOperationException {
            return seatDao.findById(id);
    }

    /**
     * Returns list of busy seat in the session from database
     * @param sessionId - id of session
     * @return list of seat
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Seat
     */
    @Override
    public List<Seat> findAllBusySeatForSession(Long sessionId) throws DaoOperationException {
            return seatDao.findAllBusySeatForSession(sessionId);
    }

    /**
     * Returns total number of busy seats.
     * @param sessionId - id of session
     * @return total number of busy seats
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public Long countOccupiedSeatsInTheAllSession(Long sessionId) throws DaoOperationException {
            return seatDao.countOccupiedSeatsInTheSession(sessionId);
    }
}
