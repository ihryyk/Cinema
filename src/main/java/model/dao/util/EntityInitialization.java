package model.dao.util;

import exception.DaoOperationException;
import model.entity.*;
import model.enums.MovieFormat;
import model.enums.UserRole;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Collections;
/**
 *
 * A class that initializes with entity information from the result seat
 *
 */
public class EntityInitialization {
    /**
     * Returns user which takes information from result seat
     * @param resultSet - result set which contain information about user
     * @return user which takes information from result seat
     * @throws SQLException if there was an error executing the query
     *                      in the database
     */
    public static User userInitialization(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id_user"));
        user.setEmailAddress(resultSet.getString("email_address"));
        user.setPassword(resultSet.getString("password"));
        user.setCreateDate(resultSet.getTimestamp("create_date"));
        user.setUpdateDate(resultSet.getTimestamp("update_date"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setPassword(resultSet.getString("password"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setRole(UserRole.valueOf(resultSet.getString("role")));
        return user;
    }

    /**
     * Returns movie which takes information from result seat
     * @param resultSet - result set which contain information about movie
     * @return user which takes information from result seat
     * @throws SQLException if there was an error executing the query
     *                      in the database
     * @throws IOException      if I/O error occurs.
     */
    public static Movie movieInitialization(ResultSet resultSet) throws SQLException, IOException {
        Movie movie = new Movie();
        movie.setId(resultSet.getLong("id_movie"));
        movie.setOriginalName(resultSet.getString("original_name"));
        movie.setReleaseDate(resultSet.getTimestamp("release_date"));
        movie.setAvailableAge(resultSet.getShort("available_age"));
        movie.setDeleted(resultSet.getBoolean("deleted"));
        movie.setPoster(resultSet.getBinaryStream("poster"));
        if (movie.getPoster()!=null){
            movie.setBase64ImagePoster(Base64.getEncoder().encodeToString(movie.getPoster().readAllBytes()));
        }
        return movie;
    }

    /**
     * Returns language which takes information from result seat
     * @param resultSet - result set which contain information about language
     * @return user which takes information from result seat
     * @throws SQLException if there was an error executing the query
     *                      in the database
     */
    public static Language languageInitialization(ResultSet resultSet) throws SQLException {
        Language language = new Language();
        language.setId(resultSet.getLong("id_language"));
        language.setName(resultSet.getString("language_name"));
        return language;
    }

    /**
     * Returns movie description which takes information from result seat
     * @param resultSet - result set which contain information about movie description
     * @return user which takes information from result seat
     * @throws SQLException if there was an error executing the query
     *                      in the database
     */
    public static MovieDescription movieDescriptionInitialization(ResultSet resultSet) throws SQLException {
        MovieDescription movieDescription = new MovieDescription();
        movieDescription.setTitle(resultSet.getString("title"));
        movieDescription.setDirector(resultSet.getString("director"));
        return movieDescription;
    }

    /**
     * Returns session which takes information from result seat
     * @param resultSet - result set which contain information about session
     * @return user which takes information from result seat
     * @throws SQLException if there was an error executing the query
     *                      in the database
     * @throws IOException      if I/O error occurs.
     */
    public static Session sessionInitialization(ResultSet resultSet) throws SQLException, IOException {
        Session session = new Session();
        session.setId(resultSet.getLong("id_session"));
        session.setStartTime(resultSet.getTimestamp("start_time"));
        session.setEndTime(resultSet.getTimestamp("end_time"));
        session.setAvailableSeats(resultSet.getInt("available_seats"));
        session.setFormat(MovieFormat.valueOf(resultSet.getString("format")));
        session.setPrice(resultSet.getBigDecimal("price"));
        return session;
    }

    /**
     * Returns seat which takes information from result seat
     * @param resultSet - result set which contain information about seat
     * @return user which takes information from result seat
     * @throws SQLException if there was an error executing the query
     *                      in the database
     */
    public static Seat seatInitialization(ResultSet resultSet) throws SQLException {
            Seat seat = new Seat();
            seat.setId(resultSet.getLong("id_seat"));
            seat.setRow(resultSet.getInt("row"));
            seat.setNumber(resultSet.getInt("number"));
            return seat;
    }

    /**
     * Returns purchased seat which takes information from result seat
     * @param resultSet - result set which contain information about purchased seat
     * @return user which takes information from result seat
     * @throws SQLException if there was an error executing the query
     *                      in the database
     * @throws IOException      if I/O error occurs.
     */
    public static PurchasedSeat purchasedSeatInitialization(ResultSet resultSet) throws SQLException, IOException {
        PurchasedSeat purchasedSeat = new PurchasedSeat();
        purchasedSeat.setSeat(seatInitialization(resultSet));
        purchasedSeat.setSession(sessionInitialization(resultSet));
        return purchasedSeat;
    }

    /**
     * Returns ticket which takes information from result seat
     * @param resultSet - result set which contain information about ticket
     * @return user which takes information from result seat
     * @throws SQLException if there was an error executing the query
     *                      in the database
     * @throws IOException      if I/O error occurs.
     */
    public static Ticket ticketInitialization(ResultSet resultSet) throws SQLException, IOException {
     Ticket ticket = new Ticket();
     ticket.setId(resultSet.getLong("id_ticket"));
     ticket.setPurchaseDate(resultSet.getTimestamp("purchase_date"));
     ticket.setPurchasedSeat(purchasedSeatInitialization(resultSet));
     return ticket;
    }
}
