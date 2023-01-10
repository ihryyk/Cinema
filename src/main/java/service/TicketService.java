package service;

import exception.DaoOperationException;
import exception.ServiceException;
import model.entity.Ticket;

import java.util.List;

public interface TicketService {
    public List<Ticket> findByUserId(Long userId, Long languageId) throws ServiceException;
}
