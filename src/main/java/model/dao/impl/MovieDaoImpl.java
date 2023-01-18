package model.dao.impl;

import exception.DaoOperationException;
import exception.TransactionException;
import model.dao.MovieDao;
import model.dao.util.DaoUtil;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Language;
import model.entity.Movie;
import model.entity.MovieDescription;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implement an interface that defines different activities with movie in database.
 *
 */
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

    Logger logger = Logger.getLogger(LanguageDaoImpl.class);

    /**
     * Save new movie in database
     *
     * @param movie - information about movie
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @throws TransactionException if there was an error executing the transaction
     *                              in the database
     * @see Movie
     */
    @Override
    public void save(Movie movie) throws DaoOperationException, TransactionException {
        Connection connection = null;
        PreparedStatement prAddMovie = null;
        PreparedStatement prAddDescription = null;
        try{
            connection = DataSource.getInstance().getConnection();
            DataSourceUtil.openTransaction(connection);
            prAddMovie = connection.prepareStatement(INSERT_MOVIE, PreparedStatement.RETURN_GENERATED_KEYS);
            fillMovieStatement(movie,prAddMovie);
            DaoUtil.checkRowAffected(prAddMovie);
            Long generatedKeys = DaoUtil.fetchGeneratedId(prAddMovie);
            saveMovieDescription(connection, generatedKeys,movie.getMovieDescriptionList());
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

    /**
     * Save movie description in database
     * @param connection - connection to database
     * @param movieId - id of movie
     * @param movieDescriptionList - list of movie description which will add to database
     * @throws SQLException - if there was an error executing the query
     *                        in the database
     * @see MovieDescription
     */
    private void saveMovieDescription(Connection connection, Long movieId, List<MovieDescription> movieDescriptionList) throws SQLException {
        PreparedStatement  prAddDescription = connection.prepareStatement(INSERT_MOVIE_DESCRIPTION);
        for (MovieDescription movieDescription : movieDescriptionList ) {
            prAddDescription.setLong(1,movieId);
            fillMovieDescriptionStatement(movieDescription,prAddDescription);
            prAddDescription.executeUpdate();
        }
    }

    /**
     * Update movie in database
     * @param movie - new information about movie
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @throws TransactionException if there was an error executing the transaction
     *                              in the database
     * @see Movie
     */
    @Override
    public void update(Movie movie) throws DaoOperationException, TransactionException {
        Connection connection = null;
        PreparedStatement prUpdateMovie = null;
        try{
            connection = DataSource.getInstance().getConnection();
            DataSourceUtil.openTransaction(connection);
            prUpdateMovie = connection.prepareStatement(UPDATE_MOVIE);
            fillMovieStatement(movie,prUpdateMovie);
            prUpdateMovie.setLong(5,movie.getId());
            DaoUtil.checkRowAffected(prUpdateMovie);
            updateMovieDescription(connection,movie.getId(),movie.getMovieDescriptionList());
            DataSourceUtil.closeTransaction(connection);
        }catch (SQLException | DaoOperationException e){
            DataSourceUtil.rollback(connection);
            throw new DaoOperationException(String.format("Cannot prepare update statement for movie: %s", movie), e);
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        } finally{
            DataSourceUtil.closeConnection(connection);
            DataSourceUtil.closeStatement(prUpdateMovie);

        }
    }

    /**
     * Update movie description in database
     * @param connection - connection to database
     * @param movieId - id of movie
     * @param movieDescriptionList - list of movie description which will add to database
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @throws SQLException if there was an error executing the query
     *                      in the database
     * @see MovieDescription
     */
    private void updateMovieDescription(Connection connection, Long movieId, List<MovieDescription> movieDescriptionList) throws DaoOperationException, SQLException {
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(UPDATE_MOVIE_DESCRIPTION);
            for (MovieDescription movieDescription :movieDescriptionList ) {
                fillMovieDescriptionStatement(movieDescription,preparedStatement);
                preparedStatement.setLong(3,movieId);
                preparedStatement.executeUpdate();
                DaoUtil.checkRowAffected(preparedStatement);
            }
        }finally{
            DataSourceUtil.closeStatement(preparedStatement);
        }
    }

    /**
     * Fills in movie statement parameters
     * @param movie - information about movie
     * @param preparedStatement - prepared statement with sql request
     * @throws SQLException - if there was an error executing the query
     *                        in the database
     * @see Movie
     */
    private void fillMovieStatement(Movie movie, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1,movie.getOriginalName());
        preparedStatement.setTimestamp(2, movie.getReleaseDate());
        preparedStatement.setInt(3,movie.getAvailableAge());
        preparedStatement.setBinaryStream(4,movie.getPoster());
    }

    /**
     * Fills in movie description statement parameters
     * @param movieDescription - information about description of movie
     * @param preparedStatement - prepared statement with sql request
     * @throws SQLException - if there was an error executing the query
     *                        in the database
     * @see Movie
     */
    private void fillMovieDescriptionStatement(MovieDescription movieDescription, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1,movieDescription.getTitle());
        preparedStatement.setString(2,movieDescription.getDirector());
        preparedStatement.setLong(4,movieDescription.getLanguage().getId());
    }

    /**
     * Change a delete status in movie to 'true'
     * @param id - id of movie
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public void delete(Long id) throws DaoOperationException {
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SET_DELETE_TRUE)){
            pr.setLong(1,id);
            DaoUtil.checkRowAffected(pr);
        }catch (SQLException | DaoOperationException e){
            throw new DaoOperationException(String.format("Error deleting movie with id = ?: %d",id), e);
        }
    }

    /**
     * Returns list of movie from database by language id
     * @param languageId - id of language
     * @return list of movie.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public List<Movie> findAllByLanguage(Long languageId) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIES_BY_LANGUAGE)){
            pr.setLong(1,languageId);
            rs = pr.executeQuery();
            logger.info("Find all movie by language id - " + languageId);
            return collectToList(rs);
        } catch (SQLException | IOException  e) {
            logger.error("Error finding all movies by language id", e);
            throw new DaoOperationException("Error finding all movies by language id", e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns list of movie from database by language id and movie title
     * @param title - movie's title
     * @param languageId - id of language
     * @return list of movie.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public List<Movie> findByLanguageAndTitle(Long languageId, String title) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIES_BY_LANGUAGE_AND_TITLE)){
            pr.setString(1,title);
            pr.setLong(2,languageId);
            rs = pr.executeQuery();
            return collectToList(rs);
        } catch (SQLException | IOException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns movie from database by id
     * @param id - id of movie
     * @return movie.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
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
        } catch (SQLException | IOException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns list of films that have in the upcoming session by language id.
     * @param start    - position for retrieving data from a database
     * @param total    -  count of movies displayed on one page
     * @param languageId - id of language
     * @return list of movie.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public List<Movie> findAllWhichHaveSessionInTheFutureByLanguage(Long languageId, int start, int total) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIES_WHICH_HAVE_SESSIONS_IN_THE_FUTURE_BY_LANGUAGE_ID+ total + "OFFSET " + start)){
            pr.setLong(1,languageId);
            rs = pr.executeQuery();
            return collectToList(rs);
        } catch (SQLException | IOException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns list of films that have in the upcoming session by title and language id.
     * @param start    - position for retrieving data from a database
     * @param total    -  count of movies displayed on one page
     * @param languageId - id of language
     * @param title - book's title
     * @return list of movie.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public List<Movie> findAllWhichHaveSessionInTheFutureByLanguageAndTitle(Long languageId, String title, int start, int total) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIES_WHICH_HAVE_SESSIONS_IN_THE_FUTURE_BY_MOVIE_TITLE_AND_LANGUAGE_ID+ total + "OFFSET " + start)){
            pr.setLong(1,languageId);
            pr.setString(2,title);
            rs = pr.executeQuery();
            return collectToList(rs);
        } catch (SQLException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Reads movies information from result set and collect him to list
     * @param resultSet - result set with information about movies
     * @return list of movies
     * @throws SQLException - if there was an error executing the query
     *                        in the database
     * @throws IOException  - if I/O error occurs.
     * @see Movie
     */
    private List<Movie> collectToList(ResultSet resultSet) throws SQLException, IOException {
        List<Movie> movieList = new ArrayList<>();
        while (resultSet.next()){
            Movie movie = EntityInitialization.movieInitialization(resultSet);
            movie.getMovieDescriptionList().add(EntityInitialization.movieDescriptionInitialization(resultSet));
            movieList.add(movie);
        }
        return movieList;
    }

    /**
     * Returns movie's poster by id.
     * @param id - id of book
     * @return input stream of poster image
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public InputStream getPosterByMovieId(Long id) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_POSTER_BY_MOVIE_ID)){
            pr.setLong(1,id);
            rs = pr.executeQuery();
            if (rs.next()){
                return rs.getBinaryStream("poster");
            }else {
                throw new DaoOperationException(String.format("Movie with id = %d does not exist", id));
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns total number of movies which have active session in database.
     * @return total number of movies in database
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
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

    /**
     * Returns total number of movies which have active session in database by title and language id.
     * @param languageId - id of language
     * @param title - book's title
     * @return total number of movies in database
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
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
