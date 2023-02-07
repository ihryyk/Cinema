package service.impl;

import exception.DaoOperationException;
import model.dao.SessionDao;
import model.entity.Seat;
import model.entity.Session;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.SessionService;
import util.GetEntity;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

    private SessionService sessionService;

    @Mock
    SessionDao sessionDao;

    @BeforeEach
    void setUp() {
        sessionService = new SessionServiceImpl(sessionDao);
    }

    @Test
    void save() throws DaoOperationException {
        Session session = GetEntity.getSession();
        doNothing().when(sessionDao).save(session);
        sessionService.save(session);
        verify(sessionDao, times(1)).save(session);
    }

    @Test
    void update() throws DaoOperationException {
        Session session = GetEntity.getSession();
        doNothing().when(sessionDao).update(session);
        sessionService.update(session);
        verify(sessionDao, times(1)).update(session);
    }

    @Test
    void findByIdAndLanguageId() throws DaoOperationException {
        Session expected = GetEntity.getSession();
        when(sessionDao.findByIdAndLanguage(1L,1L)).thenReturn(expected);
        Session actual = sessionService.findByIdAndLanguageId(1L,1L);
        assertThat(
                expected,
                allOf(
                        hasProperty("format", CoreMatchers.equalTo(actual.getFormat()))));
    }

    @Test
    void sortBy() throws DaoOperationException {
        when(sessionDao.sortBy("date",1L)).thenReturn(Collections.singletonList(GetEntity.getSession()));
        List<Session> actual = sessionService.sortBy("date",1L);
        assertThat(actual, hasSize(1));
    }

    @Test
    void findByMovieId() throws DaoOperationException {
        when(sessionDao.findByMovie(1L)).thenReturn(Collections.singletonList(GetEntity.getSession()));
        List<Session> actual = sessionService.findByMovieId(1L);
        assertThat(actual, hasSize(1));
    }
}