package service.impl;

import controller.validator.ArgumentValidator;
import controller.validator.Validator;
import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.LanguageDao;
import model.entity.Language;
import service.LanguageService;

import java.util.List;
/**
 * Implement an interface that defines different activities with language.
 *
 */
public class LanguageServiceImpl implements LanguageService {
    private final LanguageDao languageDao = DaoFactory.getLanguageDao();
    /**
     * Returns list of language from database
     *
     * @return list of language.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Language
     */
    @Override
    public List<Language> findAll() throws DaoOperationException {
          return languageDao.finaAll();
    }

    /**
     * Returns id of language by language name
     *
     * @return id of language
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Language
     */
    @Override
    public Long getIdByName(String name) throws DaoOperationException {
        ArgumentValidator.checkForNullOrEmptyString(name,"An empty or null name value is not allowed");
        return languageDao.getIdByName(name);
    }
}
