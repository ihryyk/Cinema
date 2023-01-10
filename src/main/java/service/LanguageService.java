package service;

import exception.ServiceException;
import model.entity.Language;

import java.util.List;

public interface LanguageService {
    public List<Language> findAll() throws ServiceException;
}
