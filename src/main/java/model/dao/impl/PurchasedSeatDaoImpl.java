package model.dao.impl;

import exception.DaoOperationException;
import exception.TransactionException;
import model.dao.PurchasedSeatDao;
import model.dao.util.DaoUtil;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.entity.PurchasedSeat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Implement an interface that defines different activities with purchased seat in database.
 *
 */
public class PurchasedSeatDaoImpl implements PurchasedSeatDao {
    private static final String INSERT_PURCHASED_SEAT = "INSERT INTO purchased_seats(session_id, seat_id) VALUES ( ?, ?);";
    private static final String INSERT_TICKET = "INSERT INTO tickets(user_id, purchased_seat_id, purchase_date) VALUES ( ?, ?, ?);";
    private static final String UPDATE_AVAILABLE_SEAT = "UPDATE sessions SET available_seats=available_seats-1 WHERE id_session = ?;";

    /**
     * Saves new purchased seat in database. Also creates ticket and updates available seats in the session
     * @param seatId - id of seat
     * @param sessionId - id of session
     * @param userId - id of user
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see PurchasedSeat
     */
    @Override
    public void save(Long sessionId, Long seatId, Long userId) throws DaoOperationException, TransactionException {
        Connection connection = null;
        PreparedStatement prSavePurchasedSeat = null;
        try{
            connection = DataSource.getInstance().getConnection();
            DataSourceUtil.openTransaction(connection);
            prSavePurchasedSeat = connection.prepareStatement(INSERT_PURCHASED_SEAT, PreparedStatement.RETURN_GENERATED_KEYS);
            prSavePurchasedSeat.setLong(1,seatId);
            prSavePurchasedSeat.setLong(2,sessionId);
            prSavePurchasedSeat.executeUpdate();
            Long purchasedSeadId =  DaoUtil.fetchGeneratedId(prSavePurchasedSeat);
            saveTicket(connection,userId,purchasedSeadId);
            updateAvailableSeat(connection,sessionId);
            DataSourceUtil.closeTransaction(connection);
        }catch (SQLException | TransactionException e){
            DataSourceUtil.rollback(connection);
            throw new DaoOperationException("Error adding purchased seat:)");
        }finally{
            DataSourceUtil.closeConnection(connection);
            DataSourceUtil.closeStatement(prSavePurchasedSeat);
        }
    }

    /**
     * Save new ticket in database
     * @param  connection - connection to database
     * @param userId - id of user
     * @param purchasedSeadId - id of purchased seat
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @throws SQLException if there was an error executing the query
     *                      in the database
     * @see PurchasedSeat
     */
    private void saveTicket(Connection connection,long userId, long purchasedSeadId) throws SQLException, DaoOperationException {
        PreparedStatement preparedStatement = null;
        try{  preparedStatement = connection.prepareStatement(INSERT_TICKET);
        preparedStatement.setLong(1,userId);
        preparedStatement.setLong(2, purchasedSeadId);
        preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.executeUpdate();
        }finally{
           DataSourceUtil.closeStatement(preparedStatement);
       }
    }

    /**
     * Update available seat in database
     * @param  connection - connection to database
     * @param sessionId - id of session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @throws SQLException if there was an error executing the query
     *                      in the database
     * @see PurchasedSeat
     */
    private void updateAvailableSeat(Connection connection, long sessionId) throws SQLException, DaoOperationException {
        PreparedStatement preparedStatement = null;
        try{
        preparedStatement = connection.prepareStatement(UPDATE_AVAILABLE_SEAT);
        preparedStatement.setLong(1,sessionId);
        preparedStatement.executeUpdate();
        }finally{
            DataSourceUtil.closeStatement(preparedStatement);
        }
    }
}
