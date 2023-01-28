package model.dao.impl;

import exception.DaoOperationException;
import model.dao.UserDao;
import model.dao.util.DaoUtil;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Movie;
import model.entity.MovieDescription;
import model.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Implement an interface that defines different activities with user in database.
 *
 */
public class UserDaoImpl implements UserDao {
    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM USERS WHERE password = ? AND email_address = ?;";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM USERS WHERE email_address = ?;";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM USERS WHERE id_user = ?;";
    private static final String SELECT_USER_BY_PHONE_NUMBER = "SELECT * FROM USERS WHERE phone_number = ?;";
    private static final String UPDATE_CONTACT_INFORMATION = "UPDATE users " +
            "SET first_name=?, last_name=?, update_date=?, password=? " +
            "WHERE id_user=?;";
    private static final String UPDATE_EMAIL = "UPDATE users " +
            "SET email_address=?, update_date=?" +
            "WHERE id_user=?;";
    private static final String UPDATE_PHONE_NUMBER = "UPDATE users " +
            "SET phone_number=?, update_date=?" +
            "WHERE id_user=?;";
    private static final  String INSERT_USER = "INSERT INTO  users (" +
            " first_name, last_name, phone_number, email_address, create_date, update_date, password)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?);";

    private final static Logger logger = Logger.getLogger(UserDaoImpl.class);

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
        Objects.requireNonNull(password);
        Objects.requireNonNull(email);
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD)){
            pr.setString(1,password);
            pr.setString(2,email);
            rs = pr.executeQuery();
            logger.info(String.format("Fin user by password = %s and email = %s",password,email));
            return checkIfUserIsFound(rs);
        } catch (SQLException e) {
            logger.error(String.format("Error getting user by email = %s and password = %s", email, password),e);
            throw new DaoOperationException(String.format("Error getting user by email = %s and password = %s", email, password),e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
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
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_USER_BY_ID)){
            pr.setLong(1,id);
            rs = pr.executeQuery();
            logger.info(String.format("Find user by id = %d", id));
            return checkIfUserIsFound(rs);
        }catch (SQLException e){
            logger.error(String.format("Error getting user by id = %d", id),e);
            throw new DaoOperationException(String.format("Error getting user by id = %d", id),e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }

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
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_USER_BY_EMAIL)){
            pr.setString(1,email);
            rs = pr.executeQuery();
            logger.info(String.format("Find user by email = %s", email));
            return checkIfUserIsFound(rs);
        }catch (SQLException e){
            logger.error(String.format("Error getting user by email = %s", email),e);
            throw new DaoOperationException(String.format("Error getting user by email = %s", email),e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
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
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_USER_BY_PHONE_NUMBER)){
            pr.setString(1,phoneNumber);
            rs = pr.executeQuery();
            logger.info(String.format("Error getting user by phone number = %s",phoneNumber));
            return checkIfUserIsFound(rs);
        }catch (SQLException e){
            logger.error(String.format("Error getting user by phone number = %s",phoneNumber),e);
            throw new DaoOperationException(String.format("Error getting user by phone number = %s",phoneNumber),e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns user if he is in database
     * @param resultSet - result set with information about user
     * @throws SQLException if there was an error executing the query
     *                      in the database
     * @see User
     */
    private User checkIfUserIsFound(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            return EntityInitialization.userInitialization(resultSet);
        }else {
            return null;
        }
    }

    /**
     * Fills in user statement parameters
     * @param user - information about user
     * @param preparedStatement - prepared statement with sql request
     * @throws SQLException if there was an error executing the query
     *                      in the database
     * @see User
     */
    private void fillUserStatement(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1,user.getFirstName());
        preparedStatement.setString(2,user.getLastName());
        preparedStatement.setString(3,user.getPhoneNumber());
        preparedStatement.setString(4,user.getEmailAddress());
        preparedStatement.setTimestamp(5, user.getCreateDate());
        preparedStatement.setTimestamp(6,  user.getCreateDate());
        preparedStatement.setString(7,user.getPassword());
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
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(UPDATE_EMAIL)){
            pr.setString(1,email);
            pr.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pr.setLong(3,userId);
            DaoUtil.checkRowAffected(pr);
            logger.info(String.format("Update user's email address: %s", email));
        }catch (SQLException e){
            logger.error(String.format("Error updating user's email address: %s", email), e);
            throw new DaoOperationException(String.format("Error updating user's email address: %s", email), e);
        }
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
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(UPDATE_PHONE_NUMBER)){
            pr.setString(1,phoneNumber);
            pr.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pr.setLong(3,userId);
            DaoUtil.checkRowAffected(pr);
            logger.info(String.format("Update user's phone number: %s", phoneNumber));
        }catch (SQLException e){
            logger.error(String.format("Error updating user's phone number: %s", phoneNumber), e);
            throw new DaoOperationException(String.format("Error updating user's phone number: %s", phoneNumber), e);
        }
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
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(UPDATE_CONTACT_INFORMATION)){
            pr.setString(1,user.getFirstName());
            pr.setString(2,user.getLastName());
            pr.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pr.setString(4,user.getPassword());
            pr.setLong(5,user.getId());
            DaoUtil.checkRowAffected(pr);
            logger.info(String.format("Error updating user contact information: %s", user));
        }catch (SQLException e){
            logger.error(String.format("Error updating user contact information: %s", user), e);
            throw new DaoOperationException(String.format("Error updating user contact information: %s", user), e);
        }
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
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(INSERT_USER)){
            fillUserStatement(user,pr);
            pr.executeUpdate();
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error adding user: %s", user), e);
        }
    }
}
