package model.dao.impl;

import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.LanguageDao;
import model.entity.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DatabaseBuildScript;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class LanguageDaoImplTest {

    private final static LanguageDao languageDao = DaoFactory.getLanguageDao();

    @BeforeEach
    public void runScript() {
        try {
            DatabaseBuildScript.RunSqlScript();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findAllTest() throws DaoOperationException {
        List<Language> actual = languageDao.finaAll();
        assertThat(actual, hasSize(2));
    }

    @Test
    void getByIdNameTest() throws DaoOperationException {
        long expectedId = languageDao.getIdByName("eng");
        assertEquals(expectedId,1);
    }
}