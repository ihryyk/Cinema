package service;

import exception.ServiceException;
import model.entity.Movie;

import java.io.InputStream;
import java.util.List;

public interface MovieService {
    public void save(Movie movie) throws ServiceException;

    public void update(Movie movie) throws ServiceException;

    public void delete(Long id) throws ServiceException;

    public List<Movie> findAllByLanguage(Long languageId) throws ServiceException;
    public List<Movie> findByLanguageAndTitle(Long languageId, String movieName) throws ServiceException;;
    public Movie findById(Long id) throws ServiceException;
    public InputStream getPosterByMovieId(Long id) throws ServiceException;

    public List<Movie> findAllWhichHaveSessionInTheFutureByLanguage(Long languageId, int start, int total) throws ServiceException;

    public List<Movie> findAllWhichHaveSessionInTheFutureByLanguageAndTitle(Long languageId, String title, int start, int total) throws ServiceException;
    public int getCountMovieWhichHaveSessionInTheFuture() throws ServiceException;
    public int getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId(String title, Long languageId) throws ServiceException;
}
