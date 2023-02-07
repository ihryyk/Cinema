package service.impl;

import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.LanguageDao;
import model.dao.impl.LanguageDaoImpl;
import model.entity.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.LanguageService;
import service.ServiceFactory;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageServiceImplTest {
    private LanguageService languageService;

    @Mock
    LanguageDao languageDao;


    @BeforeEach
    void setUp() {
        languageService = new LanguageServiceImpl(languageDao);
    }

    @Test
    void findAll() throws DaoOperationException {
        when(languageDao.finaAll()).thenReturn(Collections.singletonList(getLanguage()));
        List<Language> actualLanguages = languageService.findAll();
        assertThat(actualLanguages, hasSize(1));
    }

    @Test
    void getIdByName() throws DaoOperationException {
        when(languageDao.getIdByName("ua")).thenReturn(1L);
        Long actualId = languageService.getIdByName("ua");
        assertThat(actualId, equalTo(1L));
    }

    private static Language getLanguage() {
        Language language = new Language();
        language.setId(1L);
        language.setName("ua");
        return language;
    }
}