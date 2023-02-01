package model.dao.impl;

import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.SessionDao;
import model.entity.Movie;
import model.entity.Session;
import model.enums.MovieFormat;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DatabaseBuildScript;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

class SessionDaoImplTest {

    private final static SessionDao sessionDao = DaoFactory.getSessionDao();

    @BeforeEach
    public void runScript() {
        try {
            DatabaseBuildScript.RunSqlScript();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void save() throws DaoOperationException {
        Session session = getSession();
        sessionDao.save(session);
        List<Session> actual = sessionDao.findByMovie(2L);
        assertEquals(3, actual.size());

    }

    @Test
    void update() throws DaoOperationException {
        Session session = getSession();
        session.setId(2L);
        session.setFormat(MovieFormat.LUX);
        sessionDao.update(session);
        Session actual = sessionDao.findByIdAndLanguage(2L,1L);
        assertThat(
                actual,
                allOf(
                        hasProperty("format", CoreMatchers.equalTo(MovieFormat.LUX))));
    }

    @Test
    void findByIdAndLanguage() throws DaoOperationException {
        Session actual = sessionDao.findByIdAndLanguage(2L,1L);
        assertThat(
                actual,
                allOf(
                        hasProperty("format", CoreMatchers.equalTo(MovieFormat.LUX))));
    }

    @Test
    void sortBy() throws DaoOperationException {
        List<Session> actual = sessionDao.sortBy("available_seats",2L);
        assertEquals(2, actual.size());
    }

    @Test
    void findByMovie() throws DaoOperationException {
        List<Session> actual = sessionDao.findByMovie(2L);
        assertEquals(2, actual.size());
    }

    private static Session getSession() {
        Session session = new Session();
        Movie movie = new Movie();
        movie.setId(2L);

        session.setMovie(movie);
        session.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        session.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        session.setPrice(BigDecimal.valueOf(100));
        session.setAvailableSeats(2);
        session.setFormat(MovieFormat.D2);
        return session;
    }
}