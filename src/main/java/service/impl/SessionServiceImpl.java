package service.impl;

import controller.validator.ArgumentValidator;
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
    private final SessionDao sessionDao;

    public SessionServiceImpl (){
        sessionDao = DaoFactory.getSessionDao();
    }

    public SessionServiceImpl (SessionDao sessionDao){
        this.sessionDao = sessionDao;
    }

    /**
     * Saves new session in database.
     * @param session - new session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public void save(Session session) throws DaoOperationException {
        ArgumentValidator.checkForNull(session,"An empty session value is not allowed");
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
        ArgumentValidator.checkForNull(session,"An empty session value is not allowed");
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
        ArgumentValidator.checkForNull(id,"An empty id value is not allowed");
        ArgumentValidator.checkForNull(languageId,"An empty id value is not allowed");
        return sessionDao.findByIdAndLanguage(id, languageId);
    }

    /**
     * Returns list of sorting of sessions by a certain parameter
     * @param sortBy - parameter by which to sort
     * @param movieId - id of movie
     * @return list of sorting of sessions by a certain parameter
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public List<Session> sortBy(String sortBy, Long movieId) throws DaoOperationException {
        ArgumentValidator.checkForNull(movieId,"An empty id value is not allowed");
        ArgumentValidator.checkForNullOrEmptyString(sortBy,"An empty or null sortBy value is not allowed");
        return sessionDao.sortBy(sortBy,movieId);
    }

    /**
     * Returns list of sessions by movie id
     * @param movieId - id of movie
     * @return list of session
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Session
     */
    @Override
    public List<Session> findByMovieId(Long movieId) throws DaoOperationException {
        ArgumentValidator.checkForNull(movieId,"An empty id value is not allowed");
        return sessionDao.findByMovie(movieId);
    }
}
