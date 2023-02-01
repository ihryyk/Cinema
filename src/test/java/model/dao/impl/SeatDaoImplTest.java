package model.dao.impl;

import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.SeatDao;
import model.dao.TicketDao;
import model.entity.Seat;
import model.entity.Session;
import model.entity.Ticket;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DatabaseBuildScript;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class SeatDaoImplTest {

    private final static SeatDao seatDao = DaoFactory.getSeatDao();

    @BeforeEach
    public void runScript() {
        try {
            DatabaseBuildScript.RunSqlScript();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findFreeSeatsBySession() throws DaoOperationException {
        List<Seat> actual = seatDao.findFreeSeatsBySession(3L);
        assertThat(actual, hasSize(1));
    }

    @Test
    void findById() throws DaoOperationException {
        Seat actual = seatDao.findById(1L);
        assertThat(
                actual,
                allOf(
                        hasProperty("number", CoreMatchers.equalTo(1)),
                        hasProperty("row", CoreMatchers.equalTo(1))));
    }

    @Test
    void findBusySeatsBySession() throws DaoOperationException {
        List<Seat> actual = seatDao.findBusySeatsBySession(2L);
        assertThat(actual, hasSize(1));
    }

    @Test
    void getNumberBusySeatsByMovie() throws DaoOperationException {
        Map<Session,Integer> actual = seatDao.getNumberBusySeatsByMovie(2L);
        assertEquals(2, actual.size());
    }

    @Test
    void ifSeatExist() throws DaoOperationException {
       boolean actual =  seatDao.ifSeatExist(1L,2L);
        assertFalse(actual);
    }
}