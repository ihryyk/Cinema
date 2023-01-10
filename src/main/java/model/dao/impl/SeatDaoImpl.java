package model.dao.impl;

import model.dao.util.DataSource;
import model.dao.SeatDao;
import exception.DaoOperationException;
import model.dao.util.DaoUtil;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
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

public class SeatDaoImpl implements SeatDao {

    private static final String INSERT_SEAT = "INSERT INTO seats(row, number) VALUES (?, ?);";
    private static final String DELETE_SEAT = "DELETE FROM seats WHERE id_seat=?;";

    private static final String SELECT_ALL_FREE_SEATS_FOR_SESSION = "SELECT * FROM seats left join  purchased_seats ps on seats.id_seat = ps.seat_id where id_purchased_seat IS NULL OR session_id!=?;";
    private static final String SELECT_SEAT_BY_ID = "SELECT * FROM seats WHERE id_seat=?;";
    @Override
    public void save(Seat seat) throws DaoOperationException {
        Objects.requireNonNull(seat);
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(INSERT_SEAT)){
            pr.setInt(1,seat.getRow());
            pr.setInt(2,seat.getNumber());
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Error saving seat %s", seat), e);
        }
    }

        @Override
        public void remove(Long id) throws DaoOperationException {
            try(Connection connection = DataSource.getInstance().getConnection();
                PreparedStatement pr = connection.prepareStatement(DELETE_SEAT)){
                pr.setLong(1,id);
                int rowsAffected = pr.executeUpdate();
                if (rowsAffected == 0){
                    throw new DaoOperationException(String.format("Seat with id = %d does not exist", id));
                }
            } catch (SQLException | DaoOperationException e) {
                throw new DaoOperationException(String.format("Error removing seat with id = %d", id), e);
            }
        }

    @Override
    public List<Seat> findAllFreeSeatForSession(Long sessionId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_ALL_FREE_SEATS_FOR_SESSION)){
            pr.setLong(1,sessionId);
            rs = pr.executeQuery();
            List<Seat> seats = new ArrayList<>();
            while (rs.next()){
                seats.add(EntityInitialization.seatInitialization(rs));
            }
            return seats;
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error finding seats for session where id = %d",sessionId), e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

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
}
