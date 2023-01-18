package service;

import exception.DaoOperationException;
import model.entity.Session;

import java.util.List;
/**
 * The interface defines methods for implementing different
 * activities with session
 *
 */
public interface SessionService {
    /**
     * Saves new session in database.
     * @param session - new session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    public void save(Session session) throws DaoOperationException;

    /**
     * Update session in database.
     * @param session - session with new information
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    public void update(Session session) throws DaoOperationException;

    /**
     * Returns information about session by id and language
     * @param id - id of session
     * @param languageId - id of language
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    public Session findByIdAndLanguageId(Long id, Long languageId) throws DaoOperationException;

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
    public List<Session> sortBy(String sortBy,Long languageId,Long movieId) throws DaoOperationException;

    /**
     * Returns list of sessions by movie id and language id
     * @param languageId - id of language
     * @param movieId - id of movie
     * @return list of session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    public List<Session> findByMovieId (Long movieId, Long languageId) throws DaoOperationException;
}
