package service;

import exception.DaoOperationException;
import exception.ServiceException;
import exception.TransactionException;
import model.entity.Movie;

import java.util.List;

public interface MovieService {
    public void save(Movie movie) throws ServiceException;

    public void update(Movie movie);

    public void delete(Long id);

    public List<Movie> findAllByLanguage(Long languageId) throws ServiceException;
    public List<Movie> findByLanguageAndTitle(Long languageId, String movieName) throws ServiceException;;
}
