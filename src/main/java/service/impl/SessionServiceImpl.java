package service.impl;

import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.SessionDao;
import model.entity.Session;
import service.SessionService;

import java.util.List;

/**
 * Implement an interface that defines different activities with session.
 *
 */
public class SessionServiceImpl implements SessionService {
    private final SessionDao sessionDao = DaoFactory.getSessionDao();

    /**
     * Saves new session in database.
     * @param session - new session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public void save(Session session) throws DaoOperationException {
           sessionDao.save(session);
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
            sessionDao.update(session);
    }

    /**
     * Returns information about session by id and language
     * @param id - id of session
     * @param languageId - id of language
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public Session findByIdAndLanguageId(Long id, Long languageId) throws DaoOperationException {
          return sessionDao.findByIdAndLanguageId(id, languageId);
    }

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
    @Override
    public List<Session> sortBy(String sortBy, Long languageId, Long movieId) throws DaoOperationException {
            return sessionDao.sortBy(sortBy,languageId,movieId);
    }

    /**
     * Returns list of sessions by movie id and language id
     * @param languageId - id of language
     * @param movieId - id of movie
     * @return list of session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public List<Session> findByMovieId(Long movieId, Long languageId) throws DaoOperationException {
           return sessionDao.findByMovieId(movieId,languageId);
    }
}
