package model.dao;

import exception.DaoOperationException;
import exception.TransactionException;
import model.entity.Movie;

import java.io.InputStream;
import java.util.List;
/**
 * The interface defines methods for implementing different
 * activities with movie
 *
 */
public interface MovieDao {

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
    public void save(Movie movie) throws DaoOperationException, TransactionException;

    /**
     * Update movie in database
     * @param movie - new information about movie
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @throws TransactionException if there was an error executing the transaction
     *                              in the database
     * @see Movie
     */
    public void update(Movie movie) throws DaoOperationException, TransactionException;

    /**
     * Change a delete status in movie to 'true'
     * @param id - id of movie
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    public void delete(Long id) throws DaoOperationException;

    /**
     * Returns list of movie from database by language id
     * @param languageId - id of language
     * @return list of movie.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    public List<Movie> findAllByLanguage(Long languageId) throws DaoOperationException;

    /**
     * Returns list of movie from database by language id and movie title
     * @param title - movie's title
     * @param languageId - id of language
     * @return list of movie.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    public List<Movie> findByLanguageAndTitle(Long languageId, String title) throws DaoOperationException;

    /**
     * Returns movie from database by id
     * @param id - id of movie
     * @return movie.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    public Movie findById (Long id) throws DaoOperationException;

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
    List<Movie> findAllWhichHaveSessionInTheFutureByLanguage(Long languageId, int start, int total) throws DaoOperationException;

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
    List<Movie> findAllWhichHaveSessionInTheFutureByLanguageAndTitle(Long languageId, String title, int start, int total) throws DaoOperationException;

    /**
     * Returns movie's poster by id.
     * @param id - id of book
     * @return input stream of poster image
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    public InputStream getPosterByMovieId(Long id) throws DaoOperationException;

    /**
     * Returns total number of movies which have active session in database.
     * @return total number of movies in database
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    int getCountMovieWhichHaveSessionInTheFuture() throws DaoOperationException;

    /**
     * Returns total number of movies which have active session in database by title and language id.
     * @param languageId - id of language
     * @param title - book's title
     * @return total number of movies in database
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Movie
     */
    int getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId(String title, Long languageId) throws DaoOperationException;

}
