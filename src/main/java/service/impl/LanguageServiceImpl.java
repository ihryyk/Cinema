package service.impl;

import exception.DaoOperationException;
import exception.ServiceException;
import model.dao.DaoFactory;
import model.dao.LanguageDao;
import model.dao.MovieDao;
import model.entity.Language;
import service.LanguageService;

import java.util.List;

public class LanguageServiceImpl implements LanguageService {
    private final LanguageDao languageDao = DaoFactory.getLanguageDao();
    @Override
    public List<Language> findAll() throws ServiceException {
        try {
          return languageDao.finaAll();
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }
}
