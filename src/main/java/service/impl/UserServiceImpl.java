package service.impl;

import exception.DaoOperationException;
import exception.ServiceException;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;
import service.UserService;
import controller.validator.ArgumentValidator;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = DaoFactory.getUserDao();
    @Override
    public User findByPasswordAndEmail(String password, String email) throws ServiceException {
        try {
            return userDao.findByPasswordAndEmail(password,email);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public User findById(long id) throws ServiceException {
        try {
            return userDao.findById(id);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public User findByEmail(String email) throws ServiceException {
        try {
            return userDao.findByEmail(email);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) throws ServiceException {
        try {
            return userDao.findByPhoneNumber(phoneNumber);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public void update(User user) throws ServiceException {
        ArgumentValidator.checkForNull(user,"Not allow for a null user in add at userService class");
        try {
            userDao.update(user);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public void save(User user) throws ServiceException {
        ArgumentValidator.checkForNull(user,"Not allow for a null user in add at userService class");
        try {
            userDao.save(user);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public void updateEmail(String email, Long userId) throws ServiceException {
        try {
           userDao.updateEmail(email, userId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public void updatePhoneNumber(String phoneNumber, Long userId) throws ServiceException {
        try {
            userDao.updatePhoneNumber(phoneNumber, userId);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    @Override
    public void updateContactInformation(User user) throws ServiceException {
        try {
            userDao.updateContactInformation(user);
        } catch (DaoOperationException e) {
            throw new ServiceException("message",e);
        }
    }

    public String passwordEncrypting(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Arrays.toString(hash);
    }
}
