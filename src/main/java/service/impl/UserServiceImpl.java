package service.impl;

import controller.validator.ArgumentValidator;
import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;
import service.UserService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * Implement an interface that defines different activities with user.
 *
 */
public class UserServiceImpl implements UserService {
    private final UserDao userDao = DaoFactory.getUserDao();

    /**
     * Returns information about user by email and password
     *
     * @param password   - user's password.
     * @param email - user's email.
     * @return information about user.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    @Override
    public User findByPasswordAndEmail(String password, String email) throws DaoOperationException {
        ArgumentValidator.checkForNullOrEmptyString(password,"An empty or null password value is not allowed");
        ArgumentValidator.checkForNullOrEmptyString(email,"An empty or null email value is not allowed");
        return userDao.findByPasswordAndEmail(password,email);
    }

    /**
     * Returns information about user by id
     *
     * @param id - id of user.
     * @return information about user.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    @Override
    public User findById(long id) throws DaoOperationException {
        ArgumentValidator.checkForNull(id,"An empty id value is not allowed");
        return userDao.findById(id);
    }

    /**
     * Returns information about user by email
     *
     * @param email - user's email.
     * @return information about user.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    @Override
    public User findByEmail(String email) throws DaoOperationException {
        ArgumentValidator.checkForNullOrEmptyString(email,"An empty or null email value is not allowed");
        return userDao.findByEmail(email);
    }

    /**
     * Returns information about user by phune number
     *
     * @param phoneNumber - user's phone number.
     * @return information about user.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    @Override
    public User findByPhoneNumber(String phoneNumber) throws DaoOperationException {
        ArgumentValidator.checkForNullOrEmptyString(phoneNumber,"An empty or null phone number value is not allowed");
        return userDao.findByPhoneNumber(phoneNumber);
    }

    /**
     * Saves new user in database.
     *
     * @param user - new user
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    @Override
    public void save(User user) throws DaoOperationException {
        ArgumentValidator.checkForNull(user,"Not allow for a null user in add at userService class");
        userDao.save(user);
    }

    /**
     * Updates user's email
     *
     * @param email - new user's email
     * @param userId - id of user
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    @Override
    public void updateEmail(String email, Long userId) throws DaoOperationException {
        ArgumentValidator.checkForNullOrEmptyString(email,"An empty or null email value is not allowed");
        ArgumentValidator.checkForNull(userId,"An null id value is not allowed");
        userDao.updateEmail(email, userId);
    }

    /**
     * Updates user's phone number
     *
     * @param phoneNumber - new user's phONE NUMBER
     * @param userId - id of user
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    @Override
    public void updatePhoneNumber(String phoneNumber, Long userId) throws DaoOperationException {
        ArgumentValidator.checkForNullOrEmptyString(phoneNumber,"An empty or null phone number value is not allowed");
        ArgumentValidator.checkForNull(userId,"An null id value is not allowed");
        userDao.updatePhoneNumber(phoneNumber, userId);
    }

    /**
     * Updates user's first name, last name. password.
     *
     * @param user - user with new information
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    @Override
    public void updateContactInformation(User user) throws DaoOperationException {
        ArgumentValidator.checkForNull(user,"An null user value is not allowed");
        userDao.updateContactInformation(user);
    }
}
