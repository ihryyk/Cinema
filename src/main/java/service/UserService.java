package service;

import exception.DaoOperationException;
import model.entity.User;

/**
 * The interface defines methods for implementing different
 * activities with user
 *
 */
public interface UserService {
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
    public User findByPasswordAndEmail(String password, String email) throws DaoOperationException;

    /**
     * Returns information about user by id
     *
     * @param id - id of user.
     * @return information about user.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    public User findById(long id) throws DaoOperationException;

    /**
     * Returns information about user by email
     *
     * @param email - user's email.
     * @return information about user.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    public User findByEmail(String email) throws DaoOperationException;

    /**
     * Returns information about user by phune number
     *
     * @param phoneNumber - user's phone number.
     * @return information about user.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    public User findByPhoneNumber(String phoneNumber) throws DaoOperationException;

    /**
     * Saves new user in database.
     *
     * @param user - new user
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    public void save(User user) throws DaoOperationException;

    /**
     * Updates user's email
     *
     * @param email - new user's email
     * @param userId - id of user
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    public void updateEmail(String email, Long userId) throws DaoOperationException;

    /**
     * Updates user's phone number
     *
     * @param phoneNumber - new user's phONE NUMBER
     * @param userId - id of user
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    public void updatePhoneNumber(String phoneNumber, Long userId) throws DaoOperationException;

    /**
     * Updates user's first name, last name. password.
     *
     * @param user - user with new information
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see User
     */
    public void updateContactInformation(User user) throws DaoOperationException;
}
