package model.dao.impl;

import exception.TransactionException;
import model.dao.util.DataSource;
import model.dao.MovieDao;
import exception.DaoOperationException;
import model.dao.util.DaoUtil;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Movie;
import model.entity.MovieDescription;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoImpl implements MovieDao {
    private static final String INSERT_MOVIE="INSERT INTO movies( original_name, release_date, available_age, poster) VALUES (?, ?, ?,?);";
    private static final String UPDATE_MOVIE = "UPDATE movies SET original_name=?, release_date=?, available_age=? WHERE id_movie =?;";
    private static final String SELECT_MOVIES_BY_LANGUAGE = "SELECT * FROM movies INNER JOIN movie_descriptions md on movies.id_movie = md.movie_id inner join languages l on l.id_language = md.language_id WHERE l.id_language = ?;";
    private static final String SELECT_MOVIES_BY_LANGUAGE_AND_TITLE = "SELECT * FROM movies INNER JOIN movie_descriptions md on movies.id_movie = md.movie_id inner join languages l on l.id_language = md.language_id WHERE md.title = ? AND l.id_language = ?;";
    private static final String UPDATE_MOVIE_DESCRIPTION = "UPDATE movie_descriptions SET title=?, director=? WHERE movie_id = ? and language_id=?;";
    private static final String INSERT_MOVIE_DESCRIPTION ="INSERT INTO movie_descriptions( movie_id, title, director, language_id) VALUES (?, ?, ?, ?);";
    private static final String SET_DELETE_TRUE = "UPDATE movies SET deleted=false WHERE id_movie=?";
    @Override
    public void save(Movie movie) throws DaoOperationException, TransactionException {
        Connection connection = null;
        PreparedStatement prAddMovie = null;
        PreparedStatement prAddDescription = null;
        try{
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation( Connection.TRANSACTION_READ_COMMITTED);
            prAddMovie = connection.prepareStatement(INSERT_MOVIE, PreparedStatement.RETURN_GENERATED_KEYS);
            prAddDescription = connection.prepareStatement(INSERT_MOVIE_DESCRIPTION);
            prAddMovie.setString(1,movie.getOriginalName());
            prAddMovie.setTimestamp(2, movie.getReleaseDate());
            prAddMovie.setInt(3,movie.getAvailableAge());
            prAddMovie.setBinaryStream(4,movie.getPoster());
            prAddMovie.executeUpdate();
            Long generatedKeys = DaoUtil.fetchGeneratedId(prAddMovie);
            for (MovieDescription movieDescription : movie.getMovieDescriptionList() ) {
                prAddDescription.setLong(1,generatedKeys);
                prAddDescription.setString(2,movieDescription.getTitle());
                prAddDescription.setString(3,movieDescription.getDirector());
                prAddDescription.setLong(4,movieDescription.getLanguage().getId());
                prAddDescription.executeUpdate();
            }
            DataSourceUtil.closeTransaction(connection);
        }catch (SQLException e){
            DataSourceUtil.rollback(connection);
            throw new DaoOperationException(String.format("Error adding movie: %s", movie), e);
        } catch (DaoOperationException | TransactionException e) {
            throw new RuntimeException(e);
        } finally{
            DataSourceUtil.closeConnection(connection);
            DataSourceUtil.closeStatement(prAddMovie);
            DataSourceUtil.closeStatement(prAddDescription);
        }
    }

    @Override
    public void update(Movie movie) throws DaoOperationException, TransactionException {
        Connection connection = null;
        PreparedStatement prUpdateMovie = null;
        PreparedStatement prUpdateDescription = null;
        try{
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation( Connection.TRANSACTION_READ_COMMITTED);
            prUpdateMovie = connection.prepareStatement(UPDATE_MOVIE);
            prUpdateMovie.setString(1,movie.getOriginalName());
            prUpdateMovie.setTimestamp(2,movie.getReleaseDate());
            prUpdateMovie.setShort(3,movie.getAvailableAge());
            prUpdateMovie.setLong(4,movie.getId());
            int rowsAffectedUpdateMovie = prUpdateMovie.executeUpdate();
            if (rowsAffectedUpdateMovie == 0){
                throw new DaoOperationException(String.format("Movie with id = %d does not exist", movie.getId()));
            }
            prUpdateDescription = connection.prepareStatement(UPDATE_MOVIE_DESCRIPTION);
            for (MovieDescription movieDescription : movie.getMovieDescriptionList() ) {
                prUpdateDescription.setString(1,movieDescription.getTitle());
                prUpdateDescription.setString(2,movieDescription.getDirector());
                prUpdateDescription.setLong(3,movie.getId());
                prUpdateDescription.setLong(4,movieDescription.getLanguage().getId());
                prUpdateDescription.executeUpdate();
                int rowsAffectedUpdateMovieDescription = prUpdateMovie.executeUpdate();
                if (rowsAffectedUpdateMovieDescription == 0){
                    throw new DaoOperationException(String.format("Movie description with id = %d does not exist", movie.getId()));
                }
            }
            DataSourceUtil.closeTransaction(connection);
        }catch (SQLException | DaoOperationException e){
            DataSourceUtil.rollback(connection);
            throw new DaoOperationException(String.format("Cannot prepare update statement for movie: %s", movie), e);
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        } finally{
            DataSourceUtil.closeConnection(connection);
            DataSourceUtil.closeStatement(prUpdateMovie);
            DataSourceUtil.closeStatement(prUpdateDescription);
        }
    }

    @Override
    public void delete(Long id) throws DaoOperationException {
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SET_DELETE_TRUE)){
            pr.setLong(1,id);
            int rowsAffected = pr.executeUpdate();
            if (rowsAffected == 0){
                throw new DaoOperationException(String.format("Movie with id = %d does not exist", id));
            }
        }catch (SQLException | DaoOperationException e){
            throw new DaoOperationException(String.format("Error deleting movie with id = ?: %d",id), e);
        }
    }

    @Override
    public List<Movie> findAllByLanguage(Long languageId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIES_BY_LANGUAGE)){
            pr.setLong(1,languageId);
            rs = pr.executeQuery();
            List<Movie> movieList = new ArrayList<>();
            while (rs.next()){
                movieList.add(EntityInitialization.movieInitialization(rs));
            }
            return movieList;
        } catch (SQLException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    @Override
    public List<Movie> findByLanguageAndTitle(Long languageId, String movieName) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIES_BY_LANGUAGE_AND_TITLE)){
            pr.setString(1,movieName);
            pr.setLong(2,languageId);
            rs = pr.executeQuery();
            List<Movie> movieList = new ArrayList<>();
            while (rs.next()){
                movieList.add(EntityInitialization.movieInitialization(rs));
            }
            return movieList;
        } catch (SQLException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }
}
