package model.dao.impl;

import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.SeatDao;
import model.dao.TicketDao;
import model.entity.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DatabaseBuildScript;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class TicketDaoImplTest {

    private final static TicketDao ticketDao = DaoFactory.getTicketDao();

    @BeforeEach
    public void runScript() {
        try {
            DatabaseBuildScript.RunSqlScript();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findByUser() throws DaoOperationException {
        List<Ticket> actual = ticketDao.findByUser(2L,2L);
        assertThat(actual, hasSize(1));
    }
}