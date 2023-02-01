package model.dao.impl;

import exception.DaoOperationException;
import model.dao.SessionDao;
import model.dao.util.DaoUtil;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Movie;
import model.entity.Session;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implement an interface that defines different activities with session in database.
 *
 */
public class  SessionDaoImpl implements SessionDao {

    private static final String INSERT_SESSION = "INSERT INTO sessions(movie_id, start_time, end_time, format, price, available_seats) VALUES (?, ?, ?, ? , ?, (SELECT COUNT(id_seat) FROM  seats));";
    private static final String UPDATE_SESSION = "UPDATE sessions SET movie_id=?, start_time=?, end_time=?, format=?, price=? WHERE id_session = ?;";
    private static final String DELETE_SESSION = "DELETE FROM sessions WHERE id_session = ?;";
    private static final String ORDER_SESSION_BY = "SELECT  * FROM  sessions WHERE movie_id = ? ORDER BY ";
    private static final String SELECT_SESSION_BY_MOVIE_ID = "SELECT  * FROM  sessions  WHERE movie_id =? and date(start_time) >= date(now())";
    private static final String SELECT_SESSION_BY_ID = "SELECT * FROM sessions INNER JOIN movies m on m.id_movie = sessions.movie_id INNER JOIN movie_descriptions md on m.id_movie = md.movie_id  WHERE  id_session=? and md.language_id=?;";

    private final static Logger logger = Logger.getLogger(SessionDaoImpl.class);

    /**
     * Saves new session in database.
     * @param session - new session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public void save(Session session) throws DaoOperationException {
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(INSERT_SESSION)){
            fillSessionStatement(session,pr);
            DaoUtil.checkRowAffected(pr);
            logger.info("Save new session");
        }catch (SQLException e){
            logger.error(String.format("Error saving session %s,session",session), e);
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
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(UPDATE_SESSION)){
            fillSessionStatement(session,pr);
            pr.setLong(6,session.getId());
            DaoUtil.checkRowAffected(pr);
            logger.info("Update session");
        }catch (SQLException | DaoOperationException e){
            logger.error(String.format("Error updating session %s,session",session), e);
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
        preparedStatement.setObject(4, session.getFormat().toString(), Types.OTHER);
        preparedStatement.setBigDecimal(5,session.getPrice());
    }

    /**
     * Returns information about session by id
     * @param id - id of session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public Session findByIdAndLanguage(Long id, long languageId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_SESSION_BY_ID)){
            pr.setLong(1,id);
            pr.setLong(2, languageId);
            rs = pr.executeQuery();
            if (rs.next()){
                Session session =  EntityInitialization.sessionInitialization(rs);
                Movie movie = EntityInitialization.movieInitialization(rs);
                movie.getMovieDescriptionList().add(EntityInitialization.movieDescriptionInitialization(rs));
                session.setMovie(movie);
                logger.info(String.format("Find session by id = %d and language id = %d",id,languageId));
                return session;
            }else {
                logger.error(String.format("Session with id = %d dose not exist",id));
                throw new DaoOperationException(String.format("Session with id = %d dose not exist",id));
            }
        }catch (SQLException | IOException e){
            logger.error(String.format("Error finding session with id = %d",id), e);
            throw new DaoOperationException(String.format("Error finding session with id = %d",id), e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns list of sorting of sessions by a certain parameter
     * @param sortBy - parameter by which to sort
     * @param movieId - id of movie
     * @return list of sorting of sessions by a certain parameter
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public List<Session> sortBy(String sortBy, Long movieId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(ORDER_SESSION_BY + sortBy + " DESC")){
            pr.setLong(1,movieId);
            rs = pr.executeQuery();
            logger.info(String.format("Sort sessions by %s which have movie with id = %d", sortBy, movieId));
            return collectToList(rs);
        }catch (SQLException | IOException e){
            logger.error(String.format("Error sorting session by %s",sortBy), e);
            throw new DaoOperationException(String.format("Error sorting session by %s",sortBy), e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns list of sessions by movie id
     * @param movieId - id of movie
     * @return list of session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public List<Session> findByMovie(Long movieId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_SESSION_BY_MOVIE_ID)){
            pr.setLong(1,movieId);
            rs = pr.executeQuery();
            logger.info(String.format("Find session by movie id = %d",movieId));
            return collectToList(rs);
        }catch (SQLException | IOException e){
            logger.error(String.format("Error finding session with movie id = %d",movieId), e);
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
            sessions.add(session);
        }
        return sessions;
    }
}
