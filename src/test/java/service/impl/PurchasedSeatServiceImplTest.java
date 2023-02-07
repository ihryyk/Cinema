package service.impl;

import exception.DaoOperationException;
import exception.TransactionException;
import model.dao.MovieDao;
import model.dao.MovieDescriptionDao;
import model.dao.PurchasedSeatDao;
import model.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.MovieService;
import service.PurchasedSeatService;
import util.GetEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchasedSeatServiceImplTest {

    private PurchasedSeatService purchasedSeatService;

    @Mock
    PurchasedSeatDao purchasedSeatDao;

    @BeforeEach
    void setUp() {
        purchasedSeatService = new PurchasedSeatServiceImpl(purchasedSeatDao);
    }

    @Test
    void save() throws DaoOperationException, TransactionException {
        doNothing().when(purchasedSeatDao).save(1L,1L,1L);
        purchasedSeatService.save(1L,1L,1L);
        verify(purchasedSeatDao, times(1)).save(1L,1L,1L);
    }


}