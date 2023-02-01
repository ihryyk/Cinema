package model.dao.impl;

import exception.DaoOperationException;
import model.dao.SeatDao;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Movie;
import model.entity.Seat;
import model.entity.Session;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implement an interface that defines different activities with seat in database.
 *
 */
public class SeatDaoImpl implements SeatDao {

    private static final String SELECT_ALL_FREE_SEATS_FOR_SESSION = "select * from seats where id_seat not in (select seat_id from purchased_seats where session_id = ?)";
    private static final String SELECT_ALL_BUSY_SEATS_FOR_SESSION = "SELECT * FROM seats left join  purchased_seats ps on seats.id_seat = ps.seat_id where session_id= ? ORDER BY row;";
    private static final String SELECT_SEAT_BY_ID = "SELECT * FROM seats WHERE id_seat=?;";
    private static final String SELECT_NUMBER_BUSY_SEATS_IN_SESSION = "SELECT sessions.*, count(id_purchased_seat) AS  number_purchased_seat FROM sessions LEFT JOIN purchased_seats ps on sessions.id_session = ps.session_id WHERE sessions.movie_id= ?\n" +
            "GROUP BY sessions.id_session";
    private static final String SELECT_FREE_SEAT_BY_SESSION_AND_SEAT = "select * from seats where id_seat not in (select seat_id from purchased_seats where session_id = ?) AND  id_seat= ?;";

    private final static  Logger logger = Logger.getLogger(SeatDaoImpl.class);

    /**
     * Returns list of free seat in the session from database
     * @param sessionId - id of session
     * @return list of seat
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Seat
     */
    @Override
    public List<Seat> findFreeSeatsBySession(Long sessionId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_ALL_FREE_SEATS_FOR_SESSION)){
            pr.setLong(1,sessionId);
            rs = pr.executeQuery();
            logger.info(String.format("Find seats by session id = %d", sessionId));
            return collectToList(rs);
        }catch (SQLException e){
            logger.error(String.format("Error finding free seats for session where id = %d",sessionId), e);
            throw new DaoOperationException(String.format("Error finding free seats for session where id = %d",sessionId), e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Reads seats information from result set and collect him to list
     * @param resultSet - result set with information about seats
     * @return list of seat
     * @throws SQLException - if there was an error executing the query
     *                        in the database
     * @see Seat
     */
    private List<Seat> collectToList(ResultSet resultSet) throws SQLException {
        List<Seat> seats = new ArrayList<>();
        while (resultSet.next()){
            seats.add(EntityInitialization.seatInitialization(resultSet));
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
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_SEAT_BY_ID)){
            pr.setLong(1,id);
            rs = pr.executeQuery();
            if (rs.next()){
                logger.info(String.format("Find seat by id = %d", id));
                return EntityInitialization.seatInitialization(rs);
            }else {
                logger.error(String.format("Seat with id = %d does not exist", id));
                throw new DaoOperationException(String.format("Seat with id = %d does not exist", id));
            }
        }catch (SQLException e){
            logger.error(String.format("Seat with id = %d does not exist", id),e);
            throw new DaoOperationException(String.format("Error finding seats with id = %d", id), e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
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
    public List<Seat> findBusySeatsBySession(Long sessionId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_ALL_BUSY_SEATS_FOR_SESSION)){
            pr.setLong(1,sessionId);
            rs = pr.executeQuery();
            logger.info(String.format("Find busy seat by session id = %d", sessionId));
            return collectToList(rs);
        }catch (SQLException e){
            logger.error(String.format("Error finding busy seats for session where id = %d",sessionId), e);
            throw new DaoOperationException(String.format("Error finding busy seats for session where id = %d",sessionId), e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns map of session with number of busy seat.
     * @param movieId - id of movie
     * @return ap of session with number of busy seat
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public Map<Session, Integer> getNumberBusySeatsByMovie(Long movieId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_NUMBER_BUSY_SEATS_IN_SESSION)){
            pr.setLong(1,movieId);
            rs = pr.executeQuery();
            logger.info(String.format("Get number of busy seats by movie id = %d", movieId));
            return collectToMap(rs);
        }catch (SQLException | IOException e){
            logger.error(String.format("Error count busy seats with id = %d", movieId), e);
            throw new DaoOperationException(String.format("Error count busy seats with id = %d", movieId), e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Reads session information from result set and collect him to map (where key - session, value - number of purchased seat)
     * @param resultSet - result set with information about seats
     * @return list of seat
     * @throws SQLException - if there was an error executing the query
     *                        in the database
     * @throws IOException      if I/O error occurs.
     * @see Seat
     */
    private  Map<Session, Integer> collectToMap(ResultSet resultSet) throws SQLException, IOException {
        Map<Session, Integer> sessionMap = new HashMap<>();
        while (resultSet.next()){
            sessionMap.put(EntityInitialization.sessionInitialization(resultSet),resultSet.getInt("number_purchased_seat"));
        }
        return sessionMap;
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
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_FREE_SEAT_BY_SESSION_AND_SEAT)){
            pr.setLong(1,sessionId);
            pr.setLong(2,seatId);
            rs = pr.executeQuery();
            logger.info(String.format("Check if seat is exist by seat id = %d and session id = %d ", seatId, sessionId));
            return rs.next();
        }catch (SQLException e){
            logger.error(String.format("Error finding seats with id = %d", seatId),e);
            throw new DaoOperationException(String.format("Error finding seats with id = %d", seatId), e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }
}
