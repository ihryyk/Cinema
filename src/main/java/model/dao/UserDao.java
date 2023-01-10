package model.dao;

import exception.DaoOperationException;
import model.entity.User;

public interface UserDao {
    public User findByPasswordAndEmail(String password, String Email) throws DaoOperationException;
    public User findById(long id) throws DaoOperationException;
    public User findByEmail(String email) throws DaoOperationException;
    public User findByPhoneNumber(String phoneNumber) throws DaoOperationException;
    public void update(User user) throws DaoOperationException;
    public void save(User user) throws DaoOperationException;
}
