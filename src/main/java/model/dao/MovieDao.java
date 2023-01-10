package model.dao;

import exception.DaoOperationException;
import exception.TransactionException;
import model.entity.Movie;

import java.util.List;

public interface MovieDao {
    public void save(Movie movie) throws DaoOperationException, TransactionException;

    public void update(Movie movie) throws DaoOperationException, TransactionException;

    public void delete(Long id) throws DaoOperationException;

    public List<Movie> findAllByLanguage(Long languageId) throws DaoOperationException;
    public List<Movie> findByLanguageAndTitle(Long languageId, String movieName) throws DaoOperationException;
}
