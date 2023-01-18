package service.impl;

import exception.DaoOperationException;
import exception.TransactionException;
import model.dao.DaoFactory;
import model.dao.MovieDao;
import model.dao.MovieDescriptionDao;
import model.entity.Movie;
import service.MovieService;

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
    public void update(Movie movie) throws DaoOperationException, TransactionException {
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
            return movieDao.findAllByLanguage(languageId);
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
            Movie movie =movieDao.findById(id);
            movie.setMovieDescriptionList(movieDescriptionDao.findByMovieId(id));
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
            return movieDao.findAllWhichHaveSessionInTheFutureByLanguage(languageId,start,total);
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
            return movieDao.findAllWhichHaveSessionInTheFutureByLanguageAndTitle(languageId,title,start,total);
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
            return movieDao.getCountMovieWhichHaveSessionInTheFuture();
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
            return movieDao.getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId(title,languageId);
    }
}
