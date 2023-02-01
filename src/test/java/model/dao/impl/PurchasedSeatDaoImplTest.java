package model.dao.impl;

import exception.DaoOperationException;
import exception.TransactionException;
import model.dao.DaoFactory;
import model.dao.MovieDao;
import model.dao.PurchasedSeatDao;
import model.dao.TicketDao;
import model.entity.Movie;
import model.entity.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DatabaseBuildScript;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class PurchasedSeatDaoImplTest {

    private final static PurchasedSeatDao purchasedSeatDao = DaoFactory.getPurchasedSeatDao();
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
    void save() throws DaoOperationException, TransactionException {
        purchasedSeatDao.save(3L,1L,2L);
        List<Ticket> actual = ticketDao.findByUser(2L,1L);
        assertThat(actual, hasSize(2));
    }
}