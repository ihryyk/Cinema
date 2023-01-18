package model.dao;

import exception.DaoOperationException;
import model.entity.Movie;
import model.entity.Seat;

import java.util.List;
/**
 * The interface defines methods for implementing different
 * activities with seat
 *
 */
public interface SeatDao {

    /**
     * Returns list of free seat in the session from database
     * @param sessionId - id of session
     * @return list of seat
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Seat
     */
    public List<Seat> findAllFreeSeatForSession(Long sessionId) throws DaoOperationException;

    /**
     * Returns seat by id
     * @param id - id of seat
     * @return seat
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Seat
     */
    public Seat findById(Long id) throws DaoOperationException;

    /**
     * Returns list of busy seat in the session from database
     * @param sessionId - id of session
     * @return list of seat
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Seat
     */
    public List<Seat> findAllBusySeatForSession(Long sessionId) throws DaoOperationException;

    /**
     * Returns total number of busy seats.
     * @param sessionId - id of session
     * @return total number of busy seats
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
   public Long countOccupiedSeatsInTheSession (Long sessionId) throws DaoOperationException;
}
