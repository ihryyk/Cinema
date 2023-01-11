package service;

import exception.ServiceException;
import model.entity.User;

public interface UserService {
    public User findByPasswordAndEmail(String password, String Email) throws ServiceException;
    public User findById(long id)throws ServiceException;
    public User findByEmail(String email) throws ServiceException;
    public User findByPhoneNumber(String phoneNumber) throws ServiceException;
    public void update(User user) throws ServiceException;
    public void save(User user) throws ServiceException;
    public void updateEmail(String email, Long userId) throws ServiceException;
    public void updatePhoneNumber(String phoneNumber, Long userId) throws ServiceException;
    public void updateContactInformation(User user) throws ServiceException;
}
