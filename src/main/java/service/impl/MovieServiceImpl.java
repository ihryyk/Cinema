package service.impl;

import controller.validator.ArgumentValidator;
import exception.DaoOperationException;
import exception.TransactionException;
import model.dao.DaoFactory;
import model.dao.MovieDao;
import model.dao.MovieDescriptionDao;
import model.entity.Movie;
import service.MovieService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
/**
 * Implement an interface that defines different activities with movie.
 *
 */
public class MovieServiceImpl implements MovieService {
    private final MovieDao movieDao = DaoFactory.getMovieDao();
    private final MovieDescriptionDao movieDescriptionDao = DaoFactory.getMovieDescriptionDao();

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
        ArgumentValidator.checkForNull(movie,"An empty movie value is not allowed");
        movieDao.save(movie);
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
    public void update(Movie movie) throws DaoOperationException, TransactionException, IOException {
        ArgumentValidator.checkForNull(movie,"An empty movie value is not allowed");
        if (movie.getPoster().available()==0){
            movie.setPoster(movieDao.getPosterByMovieId(movie.getId()));
        }
        movieDao.update(movie);
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
        ArgumentValidator.checkForNull(id,"An empty id value is not allowed");
        movieDao.delete(id);
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
        ArgumentValidator.checkForNull(languageId,"An empty id value is not allowed");
        return movieDao.findByLanguage(languageId);
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
        ArgumentValidator.checkForNull(languageId,"An empty id value is not allowed");
        ArgumentValidator.checkForNullOrEmptyString(title,"An empty or null title value is not allowed");
        return movieDao.findByLanguageAndTitle(languageId,title);
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
    public Movie findById(Long id) throws DaoOperationException {
        ArgumentValidator.checkForNull(id,"An empty id value is not allowed");
        Movie movie =movieDao.findById(id);
        movie.setMovieDescriptionList(movieDescriptionDao.findByMovie(id));
        return movie;
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
        ArgumentValidator.checkForNull(id,"An empty id value is not allowed");
        return movieDao.getPosterByMovieId(id);
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
        ArgumentValidator.checkForNull(languageId,"An empty id value is not allowed");
        return movieDao.findAllExistByLanguage(languageId,start,total);
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
        ArgumentValidator.checkForNull(languageId,"An empty id value is not allowed");
        ArgumentValidator.checkForNullOrEmptyString(title,"An empty or null title value is not allowed");
        return movieDao.findExistByLanguageAndTitle(languageId,title,start,total);
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
        return movieDao.getCountExist();
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
        ArgumentValidator.checkForNull(languageId,"An empty id value is not allowed");
        ArgumentValidator.checkForNullOrEmptyString(title,"An empty or null title value is not allowed");
        return movieDao.getCountExistByTitleAndLanguageId(title,languageId);
    }

    /**
     * Returns list of films that have in the today session.
     * @param start    - position for retrieving data from a database
     * @param total    -  count of movies displayed on one page
     * @param languageId - id of language
     * @return list of movie.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public List<Movie> findAllWhichHaveSessionToday(Long languageId, int start, int total) throws DaoOperationException {
        ArgumentValidator.checkForNull(languageId,"An empty id value is not allowed");
        return movieDao.findExistTodayByLanguage(languageId,start,total);
    }

    /**
     * Returns total number of movies which have session today in database.
     * @return total number of movies in database
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public int getCountMovieWhichHaveSessionToday() throws DaoOperationException {
        return movieDao.getCountOfExistToday();
    }

    /**
     * Returns movie from database by id and languageId
     * @param id - id of movie
     * @param languageId - id of language
     * @return movie.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    @Override
    public Movie findByIdAndLanguageId(Long id, Long languageId) throws DaoOperationException {
        ArgumentValidator.checkForNull(id,"An empty id value is not allowed");
        ArgumentValidator.checkForNull(languageId,"An empty id value is not allowed");
        return movieDao.findByIdAndLanguage(id,languageId);
    }
}
