package model.dao;

import exception.DaoOperationException;
import model.entity.Session;

import java.util.List;

public interface SessionDao {
    public void save(Session session) throws DaoOperationException;
    public void update(Session session) throws DaoOperationException;
    public void delete(Long id) throws DaoOperationException;
    public Session findById(Long id, Long languageId) throws DaoOperationException;
    public List<Session> sortBy(String sortBy,Long languageId,Long movieId) throws DaoOperationException;
    public List<Session> findAll(Long languageId);
    public List<Session> findByMovieId (Long movieId, Long languageId) throws DaoOperationException;
}
