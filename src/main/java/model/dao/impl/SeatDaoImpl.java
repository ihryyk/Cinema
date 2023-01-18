package model.dao.impl;

import model.dao.util.DataSource;
import model.dao.SeatDao;
import exception.DaoOperationException;
import model.dao.util.DaoUtil;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Movie;
import model.entity.MovieDescription;
import model.entity.Seat;
import model.entity.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implement an interface that defines different activities with seat in database.
 *
 */
public class SeatDaoImpl implements SeatDao {

    private static final String INSERT_SEAT = "INSERT INTO seats(row, number) VALUES (?, ?);";
    private static final String DELETE_SEAT = "DELETE FROM seats WHERE id_seat=?;";

    private static final String SELECT_ALL_FREE_SEATS_FOR_SESSION = "SELECT * FROM seats left join  purchased_seats ps on seats.id_seat = ps.seat_id where id_purchased_seat IS NULL OR session_id!=? ORDER BY row;";
    private static final String SELECT_ALL_BUSY_SEATS_FOR_SESSION = "SELECT * FROM seats left join  purchased_seats ps on seats.id_seat = ps.seat_id where session_id= ? ORDER BY row;";
    private static final String SELECT_SEAT_BY_ID = "SELECT * FROM seats WHERE id_seat=?;";
    private static final String SELECT_NUMBER_BUSY_SEATS_IN_SESSION = "SELECT COUNT(*) FROM purchased_seats inner join sessions s on s.id_session = purchased_seats.session_id WHERE movie_id = ?";


//    @Override
//    public void save(Seat seat) throws DaoOperationException {
//        try(Connection connection = DataSource.getInstance().getConnection();
//            PreparedStatement pr = connection.prepareStatement(INSERT_SEAT)){
//            pr.setInt(1,seat.getRow());
//            pr.setInt(2,seat.getNumber());
//            pr.executeUpdate();
//        } catch (SQLException e) {
//            throw new DaoOperationException(String.format("Error saving seat %s", seat), e);
//        }
//    }

//        @Override
//        public void remove(Long id) throws DaoOperationException {
//            try(Connection connection = DataSource.getInstance().getConnection();
//                PreparedStatement pr = connection.prepareStatement(DELETE_SEAT)){
//                pr.setLong(1,id);
//                int rowsAffected = pr.executeUpdate();
//                if (rowsAffected == 0){
//                    throw new DaoOperationException(String.format("Seat with id = %d does not exist", id));
//                }
//            } catch (SQLException | DaoOperationException e) {
//                throw new DaoOperationException(String.format("Error removing seat with id = %d", id), e);
//            }
//        }

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
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_ALL_FREE_SEATS_FOR_SESSION)){
            pr.setLong(1,sessionId);
            rs = pr.executeQuery();
            return collectToList(rs);
        }catch (SQLException e){
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
                return EntityInitialization.seatInitialization(rs);
            }else {
                throw new DaoOperationException(String.format("Seat with id = %d does not exist", id));
            }
        }catch (SQLException e){
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
    public List<Seat> findAllBusySeatForSession(Long sessionId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_ALL_BUSY_SEATS_FOR_SESSION)){
            pr.setLong(1,sessionId);
            rs = pr.executeQuery();
            return collectToList(rs);
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error finding busy seats for session where id = %d",sessionId), e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
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
    public Long countOccupiedSeatsInTheSession(Long sessionId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_NUMBER_BUSY_SEATS_IN_SESSION)){
            pr.setLong(1,sessionId);
            rs = pr.executeQuery();
            if (rs.next()){
                return rs.getLong(1);
            }else {
                throw new DaoOperationException(String.format("Session with id = %d does not exist", sessionId));
            }
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error count busy seats with id = %d", sessionId), e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }
}
