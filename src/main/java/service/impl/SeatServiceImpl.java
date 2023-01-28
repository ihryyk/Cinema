package service.impl;

import controller.validator.ArgumentValidator;
import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.SeatDao;
import model.entity.Movie;
import model.entity.Seat;
import model.entity.Session;
import service.SeatService;

import java.util.*;

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
        ArgumentValidator.checkForNull(sessionId,"An empty id value is not allowed");
        return seatDao.findFreeSeatsBySession(sessionId);
    }

    @Override
    public Map<Seat,Boolean> findSeatsSession(Long sessionId) throws DaoOperationException {
        ArgumentValidator.checkForNull(sessionId,"An empty id value is not allowed");
        List<Seat> busySeat = seatDao.findBusySeatsBySession(sessionId);
        List<Seat> freeSeat = seatDao.findFreeSeatsBySession(sessionId);
        Map<Seat,Boolean> seats = new HashMap<>();
        for (Seat seat : busySeat) {
            seats.put(seat, false);
        }
        for (Seat seat : freeSeat) {
            seats.put(seat, true);
        }
        return seats;
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
        ArgumentValidator.checkForNull(id,"An empty id value is not allowed");
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
        ArgumentValidator.checkForNull(sessionId,"An empty id value is not allowed");
        return seatDao.findBusySeatsBySession(sessionId);
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
    public Map<Session,Integer> getNumberBusySeatAllSessionByMovieId(Long sessionId) throws DaoOperationException {
        ArgumentValidator.checkForNull(sessionId,"An empty id value is not allowed");
        return seatDao.getNumberBusySeatsByMovie(sessionId);
    }

    /**
     * Returns true if seat with this id and this sessionId exist.
     * @param sessionId - id of session
     * @param seatId - id of seat
     * @return true if seat with this id and this sessionId exist
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Seat
     */
    @Override
    public boolean ifSeatExist(Long seatId, Long sessionId) throws DaoOperationException {
        ArgumentValidator.checkForNull(sessionId,"An empty id value is not allowed");
        ArgumentValidator.checkForNull(seatId,"An empty id value is not allowed");
        return seatDao.ifSeatExist(seatId,sessionId);
    }
}
