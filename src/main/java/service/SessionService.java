package service;

import exception.DaoOperationException;
import exception.ServiceException;
import model.entity.Session;

import java.util.List;

public interface SessionService {
    public void save(Session session) throws ServiceException;
    public void update(Session session)  throws ServiceException;
    public void delete(Long id)  throws ServiceException;
    public Session findById(Long id, Long languageId)  throws ServiceException;
    public List<Session> sortBy(String sortBy,Long languageId,Long movieId) throws ServiceException;
    public List<Session> findAll(Long languageId);
    public List<Session> findByMovieId (Long movieId, Long languageId)  throws ServiceException;
}
