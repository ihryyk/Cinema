package model.dao.impl;

import exception.DaoOperationException;
import exception.TransactionException;
import model.dao.DaoFactory;
import model.dao.MovieDao;
import model.entity.Language;
import model.entity.Movie;
import model.entity.MovieDescription;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DatabaseBuildScript;
import util.GetEntity;

import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MovieDaoImplTest {

    private final static MovieDao movieDao = DaoFactory.getMovieDao();

    @BeforeEach
    public void runScript() {
        try {
            DatabaseBuildScript.RunSqlScript();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveTest() throws DaoOperationException, TransactionException {
        Movie expected = GetEntity.getMovie();
        movieDao.save(expected);
        Movie actual = movieDao.findByLanguageAndTitle(2L, "title").get(0);
        assertThat(
                actual,
                allOf(
                        hasProperty("originalName", CoreMatchers.equalTo(expected.getOriginalName())),
                        hasProperty("availableAge", CoreMatchers.equalTo(expected.getAvailableAge()))));
    }

    @Test
    void updateTest() throws DaoOperationException, TransactionException {
        Movie expected = GetEntity.getMovie();
        expected.setId(2L);
        movieDao.update(expected);
        Movie actual = movieDao.findByLanguageAndTitle(2L, "title").get(0);
        assertThat(
                actual,
                allOf(
                        hasProperty("originalName", CoreMatchers.equalTo(expected.getOriginalName())),
                        hasProperty("availableAge", CoreMatchers.equalTo(expected.getAvailableAge()))));
    }

    @Test
    void deleteTest() throws DaoOperationException {
        movieDao.delete(2L);
        Movie actual = movieDao.findById(2L);
        assertThat(
                actual,
                allOf(
                        hasProperty("deleted", CoreMatchers.equalTo(true))));
    }

    @Test
    void findByIdTest() throws DaoOperationException {
        Movie actual = movieDao.findById(2L);
        assertEquals(actual.getOriginalName(), "originalName");
    }

    @Test
    void findByLanguageTest() throws DaoOperationException {
        List<Movie> actual = movieDao.findByLanguage(1L);
        assertThat(actual, hasSize(1));
    }

    @Test
    void findByLanguageAndTitleTest() throws DaoOperationException {
        List<Movie> actual = movieDao.findByLanguageAndTitle(1L, "title1");
        assertThat(actual, hasSize(1));
    }

    @Test
    void findByIdAndLanguageIdTest() throws DaoOperationException {
        Movie actual = movieDao.findByIdAndLanguage(2L, 1L);
        assertEquals(actual.getMovieDescriptionList().get(0).getTitle(), "title1");
    }

    @Test
    void findAllExistByLanguageAndTitleTest() throws DaoOperationException {
        List<Movie> actual = movieDao.findExistByLanguageAndTitle(1L, "title1", 0, 1);
        assertThat(actual, hasSize(1));
    }

    @Test
    void findExistTodayByLanguageTest() throws DaoOperationException {
        List<Movie> actual = movieDao.findExistTodayByLanguage(1L, 0, 1);
        assertThat(actual, hasSize(1));
    }

    @Test
    void findAllExistByLanguageTest() throws DaoOperationException {
        List<Movie> actual = movieDao.findAllExistByLanguage(1L, 0, 1);
        assertThat(actual, hasSize(1));
    }

    @Test
    void getPosterByMovieIdTest() throws DaoOperationException {
        InputStream actual = movieDao.getPosterByMovieId(2L);
        assertThat(actual, is(nullValue()));
    }

    @Test
    void getCountExistTest() throws DaoOperationException {
        int expected = 1;
        int actual = movieDao.getCountExist();
        assertEquals(expected, actual);
    }

    @Test
    void getCountOfExistTodayTest() throws DaoOperationException {
        int expected = 1;
        int actual = movieDao.getCountOfExistToday();
        assertEquals(expected, actual);
    }

    @Test
    void getCountExistByTitleAndLanguageIdTest() throws DaoOperationException {
        int expected = 1;
        int actual = movieDao.getCountExistByTitleAndLanguageId("title1", 1L);
        assertEquals(expected, actual);
    }


}