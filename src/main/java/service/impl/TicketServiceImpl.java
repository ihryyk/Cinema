package service.impl;

import exception.DaoOperationException;
import exception.ServiceException;
import model.dao.DaoFactory;
import model.dao.TicketDao;
import model.entity.Ticket;
import service.TicketService;

import java.util.List;

public class TicketServiceImpl implements TicketService {
    private final TicketDao ticketDao = DaoFactory.getTicketDao();
    @Override
    public List<Ticket> findByUserId(Long userId, Long languageId) throws ServiceException {
        try {
            return ticketDao.findByUserId(userId, languageId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }
}
