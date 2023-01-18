package model.dao.impl;

import exception.DaoOperationException;
import model.dao.SessionDao;
import model.dao.util.DaoUtil;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Movie;
import model.entity.MovieDescription;
import model.entity.PurchasedSeat;
import model.entity.Session;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implement an interface that defines different activities with session in database.
 *
 */
public class SessionDaoImpl implements SessionDao {

    private static final String INSERT_SESSION = "INSERT INTO sessions(movie_id, start_time, end_time, available_seats, format, price) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_SESSION = "UPDATE sessions SET movie_id=?, start_time=?, end_time=?, available_seats=?, format=?, price=? WHERE id_session = ?;";
    private static final String DELETE_SESSION = "DELETE FROM sessions WHERE id_session = ?;";
    private static final String ORDER_SESSION_BY = "SELECT  * FROM  sessions INNER JOIN movies m on m.id_movie = sessions.movie_id INNER JOIN movie_descriptions md on sessions.movie_id = md.movie_id INNER JOIN languages l on l.id_language = md.language_id WHERE md.language_id = ? AND md.movie_id = ? ORDER BY ";
    private static final String SELECT_SESSION_BY_MOVIE_ID = "SELECT  * FROM  sessions INNER JOIN movies m on m.id_movie = sessions.movie_id INNER JOIN movie_descriptions md on sessions.movie_id = md.movie_id INNER JOIN languages l on l.id_language = md.language_id WHERE md.language_id = ? AND m.id_movie =?;";
    private static final String SELECT_SESSION_BY_ID = "SELECT * FROM sessions INNER JOIN movies m on m.id_movie = sessions.movie_id INNER JOIN movie_descriptions md on m.id_movie = md.movie_id INNER JOIN languages l on l.id_language = md.language_id WHERE  id_session=? and language_id=?;";

    /**
     * Saves new session in database.
     * @param session - new session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public void save(Session session) throws DaoOperationException {
        Objects.requireNonNull(session);
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(INSERT_SESSION)){
            fillSessionStatement(session,pr);
            DaoUtil.checkRowAffected(pr);
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error saving session %s",session), e);
        }
    }

    /**
     * Update session in database.
     * @param session - session with new information
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public void update(Session session) throws DaoOperationException {
        Objects.requireNonNull(session);
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(UPDATE_SESSION)){
            fillSessionStatement(session,pr);
            pr.setLong(7,session.getId());
            DaoUtil.checkRowAffected(pr);
        }catch (SQLException | DaoOperationException e){
            throw new DaoOperationException(String.format("Error updating session %s",session), e);
        }
    }

    /**
     * Fills in session statement parameters
     * @param session - information about session
     * @param preparedStatement - prepared statement with sql request
     * @throws SQLException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    private void fillSessionStatement(Session session, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1,session.getMovie().getId());
        preparedStatement.setTimestamp(2,session.getStartTime());
        preparedStatement.setTimestamp(3,session.getEndTime());
        preparedStatement.setInt(4,session.getAvailableSeats());
        preparedStatement.setObject(5, session.getFormat().toString(), Types.OTHER);
        preparedStatement.setBigDecimal(6,session.getPrice());
    }

    /**
     * Delete session from database.
     * @param id - id of session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public void delete(Long id) throws DaoOperationException {
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(DELETE_SESSION)){
            pr.setLong(1,id);
            DaoUtil.checkRowAffected(pr);
        }catch (SQLException | DaoOperationException e){
            throw new DaoOperationException(String.format("Error updating session with id = %d",id), e);
        }
    }

    /**
     * Returns information about session by id and language
     * @param id - id of session
     * @param languageId - id of language
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public Session findByIdAndLanguageId(Long id, Long languageId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_SESSION_BY_ID)){
            pr.setLong(1,id);
            pr.setLong(2,languageId);
            rs = pr.executeQuery();
            if (rs.next()){
                Session session =  EntityInitialization.sessionInitialization(rs);
                session.getMovie().getMovieDescriptionList().add(EntityInitialization.movieDescriptionInitialization(rs));
                return session;
            }else {
                throw new DaoOperationException(String.format("Session with id = %d dose not exist",id));
            }
        }catch (SQLException | IOException e){
            throw new DaoOperationException(String.format("Error finding session with id = %d",id), e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns list of sorting of sessions by a certain parameter
     * @param sortBy - parameter by which to sort
     * @param languageId - id of language
     * @param movieId - id of movie
     * @return list of sorting of sessions by a certain parameter
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public List<Session> sortBy(String sortBy,Long languageId, Long movieId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(ORDER_SESSION_BY + sortBy)){
            pr.setLong(1,languageId);
            pr.setLong(2,movieId);
            rs = pr.executeQuery();
            return collectToList(rs);
        }catch (SQLException e){
            throw new DaoOperationException("Error sorting session by available seats", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns list of sessions by movie id and language id
     * @param languageId - id of language
     * @param movieId - id of movie
     * @return list of session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public List<Session> findByMovieId(Long movieId, Long languageId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_SESSION_BY_MOVIE_ID)){
            pr.setLong(1,languageId);
            pr.setLong(2,movieId);
            rs = pr.executeQuery();
            return collectToList(rs);
        }catch (SQLException | IOException e){
            throw new DaoOperationException(String.format("Error finding session with movie id = %d",movieId), e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Reads session information from result set and collect him to list
     * @param resultSet - result set with information about sessions
     * @return list of session
     * @throws SQLException - if there was an error executing the query
     *                        in the database
     * @throws IOException      if I/O error occurs.
     * @see Session
     */
    private List<Session> collectToList(ResultSet resultSet) throws SQLException, IOException {
        List<Session> sessions = new ArrayList<>();
        while (resultSet.next()){
            Session session =  EntityInitialization.sessionInitialization(resultSet);
            session.getMovie().getMovieDescriptionList().add(EntityInitialization.movieDescriptionInitialization(resultSet));
            sessions.add(session);
        }
        return sessions;
    }
}
