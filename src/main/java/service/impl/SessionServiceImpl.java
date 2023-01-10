package service.impl;

import exception.DaoOperationException;
import exception.ServiceException;
import model.dao.DaoFactory;
import model.dao.SessionDao;
import model.entity.Session;
import service.SessionService;

import java.util.List;

public class SessionServiceImpl implements SessionService {
    private final SessionDao sessionDao = DaoFactory.getSessionDao();

    @Override
    public void save(Session session) throws ServiceException {

    }

    @Override
    public void update(Session session) throws ServiceException {

    }

    @Override
    public void delete(Long id) throws ServiceException {

    }

    @Override
    public Session findById(Long id, Long languageId) throws ServiceException {
        try {
          return sessionDao.findById(id, languageId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public List<Session> sortBy(String sortBy, Long languageId, Long movieId) throws ServiceException {
        try {
            return sessionDao.sortBy(sortBy,languageId,movieId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public List<Session> findAll(Long languageId) {
        return null;
    }

    @Override
    public List<Session> findByMovieId(Long movieId, Long languageId) throws ServiceException {
        try {
           return sessionDao.findByMovieId(movieId,languageId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }
}
