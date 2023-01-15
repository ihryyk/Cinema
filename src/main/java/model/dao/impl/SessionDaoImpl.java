package model.dao.impl;

import exception.DaoOperationException;
import model.dao.SessionDao;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Session;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SessionDaoImpl implements SessionDao {

    private static final String INSERT_SESSION = "INSERT INTO sessions(movie_id, start_time, end_time, available_seats, format, price) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_SESSION = "UPDATE sessions SET movie_id=?, start_time=?, end_time=?, available_seats=?, format=?, price=? WHERE id_session = ?;";
    private static final String DELETE_SESSION = "DELETE FROM sessions WHERE id_session = ?;";
    private static final String ORDER_SESSION_BY = "SELECT  * FROM  sessions INNER JOIN movies m on m.id_movie = sessions.movie_id INNER JOIN movie_descriptions md on sessions.movie_id = md.movie_id INNER JOIN languages l on l.id_language = md.language_id WHERE md.language_id = ? AND md.movie_id = ? ORDER BY ";
    private static final String SELECT_SESSION_BY_MOVIE_ID = "SELECT  * FROM  sessions INNER JOIN movies m on m.id_movie = sessions.movie_id INNER JOIN movie_descriptions md on sessions.movie_id = md.movie_id INNER JOIN languages l on l.id_language = md.language_id WHERE md.language_id = ? AND m.id_movie =?;";
    private static final String SELECT_SESSION_BY_ID = "SELECT * FROM sessions INNER JOIN movies m on m.id_movie = sessions.movie_id INNER JOIN movie_descriptions md on m.id_movie = md.movie_id INNER JOIN languages l on l.id_language = md.language_id WHERE  id_session=? and language_id=?;";
    @Override
    public void save(Session session) throws DaoOperationException {
        Objects.requireNonNull(session);
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(INSERT_SESSION)){
            pr.setLong(1,session.getMovie().getId());
            pr.setTimestamp(2,session.getStartTime());
            pr.setTimestamp(3,session.getEndTime());
            pr.setInt(4,session.getAvailableSeats());
            pr.setObject(5, session.getFormat().toString(), Types.OTHER);
            pr.setBigDecimal(6,session.getPrice());
            int rowsAffected = pr.executeUpdate();
            if (rowsAffected == 0){
                throw new DaoOperationException(String.format("Session not saved %s",session));
            }
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error saving session %s",session), e);
        }
    }

    @Override
    public void update(Session session) throws DaoOperationException {
        Objects.requireNonNull(session);
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(UPDATE_SESSION)){
            pr.setLong(1,session.getMovie().getId());
            pr.setTimestamp(2,session.getStartTime());
            pr.setTimestamp(3,session.getEndTime());
            pr.setInt(4,session.getAvailableSeats());
            pr.setObject(5, session.getFormat().toString(), Types.OTHER);
            pr.setBigDecimal(6,session.getPrice());
            pr.setLong(7,session.getId());
            int rowsAffected = pr.executeUpdate();
            if (rowsAffected == 0){
                throw new DaoOperationException(String.format("Session not updated %s",session));
            }
        }catch (SQLException | DaoOperationException e){
            throw new DaoOperationException(String.format("Error updating session %s",session), e);
        }
    }

    @Override
    public void delete(Long id) throws DaoOperationException {
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(DELETE_SESSION)){
            pr.setLong(1,id);
            int rowsAffected = pr.executeUpdate();
            if (rowsAffected == 0){
                throw new DaoOperationException(String.format("Session with id = %d not deleted",id));
            }
        }catch (SQLException | DaoOperationException e){
            throw new DaoOperationException(String.format("Error updating session with id = %d",id), e);
        }
    }

    @Override
    public Session findById(Long id, Long languageId) throws DaoOperationException {
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

    @Override
    public List<Session> sortBy(String sortBy,Long languageId, Long movieId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(ORDER_SESSION_BY + sortBy)){
            pr.setLong(1,languageId);
            pr.setLong(2,movieId);
            rs = pr.executeQuery();
            List<Session> sessions = new ArrayList<>();
            while (rs.next()){
                Session session =  EntityInitialization.sessionInitialization(rs);
                session.getMovie().getMovieDescriptionList().add(EntityInitialization.movieDescriptionInitialization(rs));
                sessions.add(session);
            }
            return sessions;
        }catch (SQLException e){
            throw new DaoOperationException("Error sorting session by available seats", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Session> findAll(Long languageId) {
        return null;
    }

    @Override
    public List<Session> findByMovieId(Long movieId, Long languageId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_SESSION_BY_MOVIE_ID)){
            pr.setLong(1,languageId);
            pr.setLong(2,movieId);
            rs = pr.executeQuery();
            List<Session> sessions = new ArrayList<>();
            while (rs.next()){
                Session session =  EntityInitialization.sessionInitialization(rs);
                session.getMovie().getMovieDescriptionList().add(EntityInitialization.movieDescriptionInitialization(rs));
                sessions.add(session);
            }
            return sessions;
        }catch (SQLException | IOException e){
            throw new DaoOperationException(String.format("Error finding session with movie id = %d",movieId), e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }
}
