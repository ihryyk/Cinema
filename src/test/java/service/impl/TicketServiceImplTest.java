package service.impl;

import exception.DaoOperationException;
import model.dao.SessionDao;
import model.dao.TicketDao;
import model.entity.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.SessionService;
import service.TicketService;
import util.GetEntity;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    private TicketService ticketService;

    @Mock
    TicketDao ticketDao;

    @BeforeEach
    void setUp() {
       ticketService = new TicketServiceImpl(ticketDao);
    }


    @Test
    void findByUserId() throws DaoOperationException {
        when(ticketDao.findByUser(1L,1L)).thenReturn(Collections.singletonList(GetEntity.getTicket()));
        List<Ticket> actual = ticketService.findByUserId(1L,1L);
        assertThat(actual, hasSize(1));
    }
}