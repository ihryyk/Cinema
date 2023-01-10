package model.dao.impl;

import exception.DaoOperationException;
import exception.TransactionException;
import model.dao.PurchasedSeatDao;
import model.dao.util.DaoUtil;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PurchasedSeatDaoImpl implements PurchasedSeatDao {
    private static final String INSERT_PURCHASED_SEAT = "INSERT INTO purchased_seats(session_id, seat_id) VALUES ( ?, ?);";
    private static final String INSERT_TICKET = "INSERT INTO tickets(user_id, purchased_seat_id, purchase_date) VALUES ( ?, ?, ?);";
    private static final String UPDATE_AVAILABLE_SEAT = "UPDATE sessions SET available_seats=available_seats-1 WHERE id_session = ?;";
    @Override
    public void save(Long sessionId, Long seatId, Long userId) throws DaoOperationException, TransactionException {
        Connection connection = null;
        PreparedStatement prSavePurchasedSeat = null;
        PreparedStatement prUpdateAvailableSeats = null;
        PreparedStatement prSaveTicket = null;
        try{
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation( Connection.TRANSACTION_READ_COMMITTED);
            prSavePurchasedSeat = connection.prepareStatement(INSERT_PURCHASED_SEAT, PreparedStatement.RETURN_GENERATED_KEYS);
            prSaveTicket = connection.prepareStatement(INSERT_TICKET);
            prUpdateAvailableSeats = connection.prepareStatement(UPDATE_AVAILABLE_SEAT);
            prSavePurchasedSeat.setLong(1,seatId);
            prSavePurchasedSeat.setLong(2,sessionId);
            prSavePurchasedSeat.executeUpdate();
            Long purchasedSeadId =  DaoUtil.fetchGeneratedId(prSavePurchasedSeat);

            prSaveTicket.setLong(1,userId);
            prSaveTicket.setLong(2, purchasedSeadId);
            prSaveTicket.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            prSaveTicket.executeUpdate();

            prUpdateAvailableSeats.setLong(1,sessionId);
            prUpdateAvailableSeats.executeUpdate();

            DataSourceUtil.closeTransaction(connection);
        }catch (SQLException | TransactionException e){
            DataSourceUtil.rollback(connection);
            throw new DaoOperationException("Error adding purchased seat:)");
        }finally{
            DataSourceUtil.closeConnection(connection);
            DataSourceUtil.closeStatement(prSavePurchasedSeat);
            DataSourceUtil.closeStatement(prSaveTicket);
            DataSourceUtil.closeStatement(prUpdateAvailableSeats);
        }
    }
}
