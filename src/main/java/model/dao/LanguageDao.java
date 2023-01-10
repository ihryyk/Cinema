package model.dao;

import exception.DaoOperationException;
import model.entity.Language;

import java.util.List;

public interface LanguageDao {
    public List<Language> finaAll() throws DaoOperationException;
}
