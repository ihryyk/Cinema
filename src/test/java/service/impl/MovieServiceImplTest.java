package service.impl;

import exception.DaoOperationException;
import exception.TransactionException;
import model.dao.MovieDao;
import model.dao.MovieDescriptionDao;
import model.entity.Movie;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.MovieService;
import util.GetEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    private MovieService movieService;

    @Mock
    MovieDao movieDao;

    @Mock
    MovieDescriptionDao movieDescriptionDao;


    @BeforeEach
    void setUp() {
        movieService = new MovieServiceImpl(movieDao, movieDescriptionDao);
    }

    @Test
    void save() throws DaoOperationException, TransactionException {
        Movie movie = GetEntity.getMovie();
        doNothing().when(movieDao).save(movie);
        movieService.save(movie);
        verify(movieDao, times(1)).save(movie);
    }

    @Test
    void update() throws DaoOperationException, TransactionException, IOException {
        Movie movie = GetEntity.getMovie();
        doNothing().when(movieDao).update(movie);
        movieService.update(movie);
        verify(movieDao, times(1)).update(movie);
    }

    @Test
    void delete() throws DaoOperationException {
        doNothing().when(movieDao).delete(1L);
        movieService.delete(1L);
        verify(movieDao, times(1)).delete(1L);
    }

    @Test
    void findAllByLanguage() throws DaoOperationException {
        when(movieDao.findByLanguage(1L)).thenReturn(Collections.singletonList(GetEntity.getMovie()));
        List<Movie> actualMovies = movieService.findAllByLanguage(1L);
        assertThat(actualMovies, hasSize(1));
    }

    @Test
    void findByLanguageAndTitle() throws DaoOperationException {
        when(movieDao.findByLanguageAndTitle(1L, "title")).thenReturn(Collections.singletonList(GetEntity.getMovie()));
        List<Movie> actualMovies = movieService.findByLanguageAndTitle(1L, "title");
        assertThat(actualMovies, hasSize(1));
    }

    @Test
    void findById() throws DaoOperationException {
        Movie expected = GetEntity.getMovie();
        when(movieDao.findById(1L)).thenReturn(GetEntity.getMovie());
        when(movieDescriptionDao.findByMovie(1L)).thenReturn(GetEntity.getMovie().getMovieDescriptionList());
        Movie actual = movieService.findById(1L);
        assertThat(
                expected,
                allOf(
                        hasProperty("originalName", CoreMatchers.equalTo(actual.getOriginalName()))));

    }

    @Test
    void getPosterByMovieId() throws DaoOperationException {
        InputStream expected = InputStream.nullInputStream();
        when(movieDao.getPosterByMovieId(1L)).thenReturn(expected);
        InputStream actual = movieService.getPosterByMovieId(1L);
        assertThat(actual, equalTo(expected));
    }

    @Test
    void findAllWhichHaveSessionInTheFutureByLanguage() throws DaoOperationException {
        when(movieDao.findAllExistByLanguage(1L, 0, 1)).thenReturn(Collections.singletonList(GetEntity.getMovie()));
        List<Movie> actualMovies = movieService.findAllWhichHaveSessionInTheFutureByLanguage(1L, 0, 1);
        assertThat(actualMovies, hasSize(1));
    }

    @Test
    void findAllWhichHaveSessionInTheFutureByLanguageAndTitle() throws DaoOperationException {
        when(movieDao.findExistByLanguageAndTitle(1L, "title", 0, 1)).thenReturn(Collections.singletonList(GetEntity.getMovie()));
        List<Movie> actualMovies = movieService.findAllWhichHaveSessionInTheFutureByLanguageAndTitle(1L, "title", 0, 1);
        assertThat(actualMovies, hasSize(1));
    }

    @Test
    void getCountMovieWhichHaveSessionInTheFuture() throws DaoOperationException {
        when(movieDao.getCountExist()).thenReturn(1);
        int actualCount = movieService.getCountMovieWhichHaveSessionInTheFuture();
        assertEquals(1, actualCount);
    }

    @Test
    void getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId() throws DaoOperationException {
        when(movieDao.getCountExistByTitleAndLanguageId("title", 1L)).thenReturn(1);
        int actualCount = movieService.getCountMovieWhichHaveSessionInTheFutureByTitleAndLanguageId("title", 1L);
        assertEquals(1, actualCount);
    }

    @Test
    void findAllWhichHaveSessionToday() throws DaoOperationException {
        when(movieDao.findExistTodayByLanguage(1L, 0, 1)).thenReturn(Collections.singletonList(GetEntity.getMovie()));
        List<Movie> actualMovies = movieService.findAllWhichHaveSessionToday(1L, 0, 1);
        assertThat(actualMovies, hasSize(1));
    }

    @Test
    void getCountMovieWhichHaveSessionToday() throws DaoOperationException {
        when(movieDao.getCountOfExistToday()).thenReturn(1);
        int actualCount = movieService.getCountMovieWhichHaveSessionToday();
        assertEquals(1, actualCount);
    }

    @Test
    void findByIdAndLanguageId() throws DaoOperationException {
        Movie expected = GetEntity.getMovie();
        when(movieDao.findByIdAndLanguage(1L, 1L)).thenReturn(GetEntity.getMovie());
        Movie actual = movieService.findByIdAndLanguageId(1L, 1L);
        assertThat(
                expected,
                allOf(
                        hasProperty("originalName", CoreMatchers.equalTo(actual.getOriginalName()))));
    }

}