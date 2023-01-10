package model.dao.impl;

import model.dao.util.DataSource;
import model.dao.TicketDao;
import exception.DaoOperationException;
import model.dao.util.DaoUtil;
import model.dao.util.EntityInitialization;
import model.entity.Ticket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao {
    private static final String SELECT_TICKETS_BY_USER_ID = "SELECT * FROM tickets INNER JOIN purchased_seats ps ON ps.id_purchased_seat = tickets.purchased_seat_id INNER JOIN seats s on s.id_seat = ps.seat_id INNER JOIN sessions s2 on s2.id_session = ps.session_id inner join movies m on m.id_movie = s2.movie_id INNER JOIN movie_descriptions md on m.id_movie = md.movie_id INNER JOIN languages l on l.id_language = md.language_id WHERE user_id = ? AND  language_id = ?;";

    @Override
    public List<Ticket> findByUserId(Long userId, Long languageId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_TICKETS_BY_USER_ID)){
            pr.setLong(1,userId);
            pr.setLong(2,languageId);
            rs = pr.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (rs.next()){
                tickets.add(EntityInitialization.ticketInitialization(rs));
            }
            return tickets;
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error finding ticket with user id = %d", userId), e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
