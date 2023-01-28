package model.dao;

import exception.DaoOperationException;
import model.entity.Movie;
import model.entity.Seat;
import model.entity.Session;

import java.util.List;
import java.util.Map;

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
    public List<Seat> findFreeSeatsBySession(Long sessionId) throws DaoOperationException;

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
    public List<Seat> findBusySeatsBySession(Long sessionId) throws DaoOperationException;

    /**
     * Returns map of session with number of busy seat.
     * @param movieId - id of movie
     * @return ap of session with number of busy seat
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    public Map<Session, Integer> getNumberBusySeatsByMovie(Long movieId) throws DaoOperationException;

    /**
     * Returns true if seat with this id and this sessionId exist.
     * @param sessionId - id of session
     * @param seatId - id of seat
     * @return true if seat with this id and this sessionId exist
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Seat
     */
    boolean ifSeatExist(Long seatId, Long sessionId) throws DaoOperationException;
}
