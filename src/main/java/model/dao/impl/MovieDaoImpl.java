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
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoImpl implements MovieDao {
    private static final String INSERT_MOVIE="INSERT INTO movies( original_name, release_date, available_age, poster) VALUES (?, ?, ?,?);";
    private static final String UPDATE_MOVIE = "UPDATE movies SET original_name=?, release_date=?, available_age=?, poster=? WHERE id_movie =?;";
    private static final String SELECT_MOVIES_BY_LANGUAGE = "SELECT * FROM movies INNER JOIN movie_descriptions md on movies.id_movie = md.movie_id WHERE language_id = ?;";
    private static final String SELECT_MOVIES_BY_LANGUAGE_AND_TITLE = "SELECT * FROM movies INNER JOIN movie_descriptions md on movies.id_movie = md.movie_id WHERE md.title = ? AND language_id = ?;";
    private static final String SELECT_MOVIES_BY_ID = "SELECT * FROM movies INNER JOIN movie_descriptions md on movies.id_movie = md.movie_id WHERE id_movie = ? ";
    private static final String SELECT_POSTER_BY_MOVIE_ID = "SELECT poster FROM movies WHERE id_movie = ?;";
    private static final String UPDATE_MOVIE_DESCRIPTION = "UPDATE movie_descriptions SET title=?, director=? WHERE movie_id = ? and language_id=?;";
    private static final String INSERT_MOVIE_DESCRIPTION ="INSERT INTO movie_descriptions( movie_id, title, director, language_id) VALUES (?, ?, ?, ?);";
    private static final String SET_DELETE_TRUE = "UPDATE movies SET deleted=true WHERE id_movie=?";
    private static final String SELECT_MOVIES_WHICH_HAVE_SESSIONS_IN_THE_FUTURE_BY_LANGUAGE_ID = "SELECT t.*, md.* FROM (\n" +
            "                                                       SELECT movies.*, count(sessions.id_session) AS number_session\n" +
            "                                                       FROM movies\n" +
            "                                                                JOIN sessions ON sessions.movie_id = movies.id_movie WHERE start_time> now()\n" +
            "                                                       GROUP BY movies.id_movie\n" +
            "                                                       HAVING count(sessions.id_session) > 0\n" +
            "                                                   ) t\n" +
            "                                                    inner join movie_descriptions md on t.id_movie = md.movie_id WHERE language_id= ? AND t.deleted IS NOT TRUE LIMIT ";
    private static final String SELECT_MOVIES_WHICH_HAVE_SESSIONS_IN_THE_FUTURE_BY_MOVIE_TITLE_AND_LANGUAGE_ID = "SELECT t.*, md.* FROM (\n" +
            "                                                       SELECT movies.*, count(sessions.id_session) AS number_session\n" +
            "                                                       FROM movies\n" +
            "                                                                JOIN sessions ON sessions.movie_id = movies.id_movie WHERE start_time> now()\n" +
            "                                                       GROUP BY movies.id_movie\n" +
            "                                                       HAVING count(sessions.id_session) > 0\n" +
            "                                                   ) t\n" +
            "                                                    inner join movie_descriptions md on t.id_movie = md.movie_id WHERE language_id= ? AND md.title=? AND t.deleted IS NOT TRUE LIMIT ";
    private static final String SELECT_COUNT_MOVIES_WHICH_HAVE_SESSIONS_IN_THE_FUTURE = "SELECT COUNT(id_movie) FROM (SELECT movies.* FROM movies JOIN sessions ON sessions.movie_id = movies.id_movie WHERE start_time> now() GROUP BY movies.id_movie HAVING count(sessions.id_session) > 0) t WHERE t.deleted IS NOT TRUE;";
    private static final String SELECT_COUNT_MOVIES_WHICH_HAVE_SESSIONS_IN_THE_FUTURE_BY_LANGUAGE_AND_TITLE = "SELECT COUNT(id_movie) FROM (SELECT movies.* FROM movies JOIN sessions ON sessions.movie_id = movies.id_movie WHERE start_time> now() GROUP BY movies.id_movie HAVING count(sessions.id_session) > 0) t INNER JOIN movie_descriptions md on t.id_movie = md.movie_id WHERE t.deleted IS NOT TRUE AND md.title = ? AND  md.language_id = ?;";
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
            prUpdateMovie.setBinaryStream(4,movie.getPoster());
            prUpdateMovie.setLong(5,movie.getId());
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
            Movie movie = null;
            while (rs.next()){
                movie = EntityInitialization.movieInitialization(rs);
                movie.getMovieDescriptionList().add(EntityInitialization.movieDescriptionInitialization(rs));
                movieList.add(movie);
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
            Movie movie = null;
            while (rs.next()){
               movie = EntityInitialization.movieInitialization(rs);
               movie.getMovieDescriptionList().add(EntityInitialization.movieDescriptionInitialization(rs));
               movieList.add(movie);
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
    public Movie findById(Long id) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIES_BY_ID)){
            pr.setLong(1,id);
            rs = pr.executeQuery();
            if (rs.next()){
                return (EntityInitialization.movieInitialization(rs)); //перенести всі методи set в dao
            }else {
                throw new DaoOperationException(String.format("Movie with id = %d does not exist", id));
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    @Override
    public List<Movie> findAllWhichHaveSessionInTheFutureByLanguage(Long languageId, int start, int total) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIES_WHICH_HAVE_SESSIONS_IN_THE_FUTURE_BY_LANGUAGE_ID+ total + "OFFSET " + start)){
            pr.setLong(1,languageId);
             rs = pr.executeQuery();
            List<Movie> movieList = new ArrayList<>();
            Movie movie = null;
            while (rs.next()){
                movie = EntityInitialization.movieInitialization(rs);
                movie.getMovieDescriptionList().add(EntityInitialization.movieDescriptionInitialization(rs));
                movieList.add(movie);
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
    public List<Movie> findAllWhichHaveSessionInTheFutureByLanguageAndTitle(Long languageId, String title, int start, int total) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIES_WHICH_HAVE_SESSIONS_IN_THE_FUTURE_BY_MOVIE_TITLE_AND_LANGUAGE_ID+ total + "OFFSET " + start)){
            pr.setLong(1,languageId);
            pr.setString(2,title);
            rs = pr.executeQuery();
            List<Movie> movieList = new ArrayList<>();
            Movie movie = null;
            while (rs.next()){
                movie = EntityInitialization.movieInitialization(rs);
                movie.getMovieDescriptionList().add(EntityInitialization.movieDescriptionInitialization(rs));
                movieList.add(movie);
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
    public InputStream getPosterByMovieId(Long id) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_POSTER_BY_MOVIE_ID)){
            pr.setLong(1,id);
            rs = pr.executeQuery();
            if (rs.next()){
                return rs.getBinaryStream("poster"); //перенести всі методи set в dao
            }else {
                throw new DaoOperationException(String.format("Movie with id = %d does not exist", id));
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    @Override
    public int getCountMovieWhichHaveSessionInTheFuture() throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_COUNT_MOVIES_WHICH_HAVE_SESSIONS_IN_THE_FUTURE)){
            rs = pr.executeQuery();
            if (rs.next()){
               return rs.getInt(1);
            }else {
                throw new DaoOperationException("Error counting the number of movies");
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Error counting the number of movies");
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    @Override
    public int getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId(String title, Long languageId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_COUNT_MOVIES_WHICH_HAVE_SESSIONS_IN_THE_FUTURE_BY_LANGUAGE_AND_TITLE)){
            pr.setString(1,title);
            pr.setLong(2,languageId);
            rs = pr.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }else {
                throw new DaoOperationException("Error counting the number of movies");
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Error counting the number of movies");
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }
}
