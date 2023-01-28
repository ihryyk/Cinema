package service;

import exception.DaoOperationException;
import model.entity.Language;

import java.util.List;
/**
 * The interface defines methods for implementing different
 * activities with language
 *
 */
public interface LanguageService {

    /**
     * Returns list of language from database
     *
     * @return list of language.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Language
     */
    public List<Language> findAll() throws DaoOperationException;

    /**
     * Returns id of language by language name
     *
     * @return id of language
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Language
     */
    public Long getIdByName(String name) throws DaoOperationException;
}
