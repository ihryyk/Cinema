package service.impl;

import exception.DaoOperationException;
import model.dao.SeatDao;
import model.entity.Seat;
import model.entity.Session;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.SeatService;
import util.GetEntity;

import java.util.*;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    private SeatService seatService;

    @Mock
    SeatDao seatDao;

    @BeforeEach
    void setUp() {
        seatService = new SeatServiceImpl(seatDao);
    }

    @Test
    void findAllFreeSeatForSession() throws DaoOperationException {
        when(seatDao.findFreeSeatsBySession(1L)).thenReturn(Collections.singletonList(GetEntity.getSeat()));
        List<Seat> actualSeats = seatService.findAllFreeSeatForSession(1L);
        assertThat(actualSeats, hasSize(1));
    }

    @Test
    void findAllBySession() throws DaoOperationException {
        when(seatDao.findFreeSeatsBySession(1L)).thenReturn(Collections.singletonList(GetEntity.getSeat()));
        when(seatDao.findBusySeatsBySession(1L)).thenReturn(new ArrayList<>());
        Map<Seat, Boolean> actualSeats = seatService.findAllBySession(1L);
        assertThat(actualSeats.keySet(), hasSize(1));
    }

    @Test
    void findById() throws DaoOperationException {
        Seat expected = GetEntity.getSeat();
        when(seatDao.findById(1L)).thenReturn(GetEntity.getSeat());
        Seat actual = seatService.findById(1L);
        assertThat(
                expected,
                allOf(
                        hasProperty("row", CoreMatchers.equalTo(actual.getRow()))));
    }

    @Test
    void findAllBusySeatForSession() throws DaoOperationException {
        when(seatDao.findBusySeatsBySession(1L)).thenReturn(Collections.singletonList(GetEntity.getSeat()));
        List<Seat> actualSeats = seatService.findAllBusySeatForSession(1L);
        assertThat(actualSeats, hasSize(1));
    }

    @Test
    void getNumberBusySeatAllSessionByMovieId() throws DaoOperationException {
        Map<Session, Integer> seats = new HashMap<>();
        seats.put(GetEntity.getSession(),1);
        when(seatDao.getNumberBusySeatsByMovie(1L)).thenReturn(seats);
        Map<Session, Integer> actualSeats = seatService.getNumberBusySeatAllSessionByMovieId(1L);
        assertThat(actualSeats.keySet(), hasSize(1));
    }

    @Test
    void ifSeatExist() throws DaoOperationException {
        when(seatDao.ifSeatExist(1L,1L)).thenReturn(true);
        boolean actual = seatService.ifSeatExist(1L,1L);
        assertTrue(actual);
    }
}