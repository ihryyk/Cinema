package service.impl;

import exception.DaoOperationException;
import exception.ServiceException;
import exception.TransactionException;
import model.dao.DaoFactory;
import model.dao.MovieDao;
import model.dao.UserDao;
import model.entity.Movie;
import service.MovieService;

import java.util.List;

public class MovieServiceImpl implements MovieService {
    private final MovieDao movieDao = DaoFactory.getMovieDao();

    @Override
    public void save(Movie movie) throws ServiceException {
        try {
            movieDao.save(movie);
        } catch (DaoOperationException | TransactionException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public void update(Movie movie) {

    }

    @Override
    public void delete(Long id) {

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
}
