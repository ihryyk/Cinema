package model.dao;

import exception.DaoOperationException;
import model.entity.Session;

import java.util.List;
/**
 * The interface defines methods for implementing different
 * activities with session
 *
 */
public interface SessionDao {

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
     * Delete session from database.
     * @param id - id of session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    public void delete(Long id) throws DaoOperationException;

    /**
     * Returns information about session by id and language
     * @param id - id of session
     * @param languageId - id of language
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    public Session findByIdAndLanguage(Long id, long languageId) throws DaoOperationException;

    /**
     * Returns list of sorting of sessions by a certain parameter
     * @param sortBy - parameter by which to sort
     * @param movieId - id of movie
     * @return list of sorting of sessions by a certain parameter
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    public List<Session> sortBy(String sortBy,Long movieId) throws DaoOperationException;

    /**
     * Returns list of sessions by movie id
     * @param movieId - id of movie
     * @return list of session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    public List<Session> findByMovie(Long movieId) throws DaoOperationException;
}
