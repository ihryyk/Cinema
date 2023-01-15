package service.impl;

import exception.DaoOperationException;
import exception.ServiceException;
import exception.TransactionException;
import model.dao.DaoFactory;
import model.dao.MovieDao;
import model.dao.MovieDescriptionDao;
import model.entity.Movie;
import service.MovieService;

import java.io.InputStream;
import java.util.List;

public class MovieServiceImpl implements MovieService {
    private final MovieDao movieDao = DaoFactory.getMovieDao();
    private final MovieDescriptionDao movieDescriptionDao = DaoFactory.getMovieDescriptionDao();
    @Override
    public void save(Movie movie) throws ServiceException {
        try {
            movieDao.save(movie);
        } catch (DaoOperationException | TransactionException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public void update(Movie movie) throws ServiceException {
        try {
            movieDao.update(movie);
        } catch (DaoOperationException | TransactionException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            movieDao.delete(id);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public List<Movie> findAllByLanguage(Long languageId) throws ServiceException {
        try {
            return movieDao.findAllByLanguage(languageId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public List<Movie> findByLanguageAndTitle(Long languageId, String movieName) throws ServiceException {
        try {
            return movieDao.findByLanguageAndTitle(languageId,movieName);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public Movie findById(Long id) throws ServiceException {
        try {
            Movie movie =movieDao.findById(id);
            movie.setMovieDescriptionList(movieDescriptionDao.findByMovieId(id));
            return movie;
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public InputStream getPosterByMovieId(Long id) throws ServiceException {
        try {
            return movieDao.getPosterByMovieId(id);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public List<Movie> findAllWhichHaveSessionInTheFutureByLanguage(Long languageId, int start, int total) throws ServiceException {
        try {
            return movieDao.findAllWhichHaveSessionInTheFutureByLanguage(languageId,start,total);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public List<Movie> findAllWhichHaveSessionInTheFutureByLanguageAndTitle(Long languageId, String title, int start, int total) throws ServiceException {
        try {
            return movieDao.findAllWhichHaveSessionInTheFutureByLanguageAndTitle(languageId,title,start,total);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public int getCountMovieWhichHaveSessionInTheFuture() throws ServiceException {
        try {
            return movieDao.getCountMovieWhichHaveSessionInTheFuture();
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public int getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId(String title, Long languageId) throws ServiceException {
        try {
            return movieDao.getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId(title,languageId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }
}
