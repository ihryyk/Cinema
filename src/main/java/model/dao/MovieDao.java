package model.dao;

import exception.DaoOperationException;
import exception.TransactionException;
import model.entity.Movie;

import java.io.InputStream;
import java.util.List;

public interface MovieDao {
    public void save(Movie movie) throws DaoOperationException, TransactionException;

    public void update(Movie movie) throws DaoOperationException, TransactionException;

    public void delete(Long id) throws DaoOperationException;

    public List<Movie> findAllByLanguage(Long languageId) throws DaoOperationException;
    public List<Movie> findByLanguageAndTitle(Long languageId, String movieName) throws DaoOperationException;
    public Movie findById (Long id) throws DaoOperationException;

    List<Movie> findAllWhichHaveSessionInTheFutureByLanguage(Long languageId, int start, int total) throws DaoOperationException;

    List<Movie> findAllWhichHaveSessionInTheFutureByLanguageAndTitle(Long languageId, String title, int start, int total) throws DaoOperationException;

    public InputStream getPosterByMovieId(Long id) throws DaoOperationException;

    int getCountMovieWhichHaveSessionInTheFuture() throws DaoOperationException;

    int getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId(String title, Long languageId) throws DaoOperationException;

}
