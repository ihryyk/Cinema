package model.dao;

import exception.DaoOperationException;
import model.entity.Ticket;

import java.util.List;

public interface TicketDao {
   public List<Ticket> findByUserId(Long userId, Long languageId) throws DaoOperationException;
}
