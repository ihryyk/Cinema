package model.dao.impl;

import model.dao.UserDao;
import exception.DaoOperationException;
import model.dao.util.DaoUtil;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class UserDaoImpl implements UserDao {
    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM USERS WHERE password = ? AND email_address = ?;";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM USERS WHERE email_address = ?;";
    private static final String SELECT_USER_BY_PHONE_NUMBER = "SELECT * FROM USERS WHERE phone_number = ?;";
    private static final String UPDATE_USER = "UPDATE users " +
            "SET first_name=?, last_name=?, phone_number=?, email_address=?, create_date=?, update_date=?, password=? " +
            "WHERE id=?;";
    private static final  String INSERT_USER = "INSERT INTO  users (" +
            " first_name, last_name, phone_number, email_address, create_date, update_date, password)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?);";
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
            if (rs.next()){
                return EntityInitialization.userInitialization(rs);
            }else {
              return null;
            }
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Error getting user by email = %s and password = %s", email, password),e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public User findByEmail(String email) throws DaoOperationException {
        Objects.requireNonNull(email);
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_USER_BY_EMAIL)){
            pr.setString(1,email);
            rs = pr.executeQuery();
            if (rs.next()){
                return EntityInitialization.userInitialization(rs);
            }else {
              return null;
            }
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error getting user by email = %s", email),e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_USER_BY_PHONE_NUMBER)){
            pr.setString(1,phoneNumber);
            rs = pr.executeQuery();
            if (rs.next()){
                return EntityInitialization.userInitialization(rs);
            }else {
                return null;
            }
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error getting user by phone number = %s",phoneNumber),e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    @Override
    public void update(User user) throws DaoOperationException {
        Objects.requireNonNull(user);
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(UPDATE_USER)){
            pr.setString(1,user.getFirstName());
            pr.setString(2,user.getLastName());
            pr.setString(3,user.getPhoneNumber());
            pr.setString(4,user.getEmailAddress());
            pr.setTimestamp(5, user.getCreateDate());
            pr.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            pr.setString(7,user.getPassword());
            pr.setLong(8,user.getId());
            int rowsAffected = pr.executeUpdate();
            if (rowsAffected == 0){
                throw new DaoOperationException(String.format("User with id = %d does not exist", user.getId()));
            }
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error updating user: %s", user), e);
        }
    }

    @Override
    public void save(User user) throws DaoOperationException {
        Objects.requireNonNull(user);
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(INSERT_USER)){
            pr.setString(1,user.getFirstName());
            pr.setString(2,user.getLastName());
            pr.setString(3,user.getPhoneNumber());
            pr.setString(4,user.getEmailAddress());
            pr.setTimestamp(5, user.getCreateDate());
            pr.setTimestamp(6,  user.getCreateDate());
            pr.setString(7,user.getPassword());
            pr.executeUpdate();
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error adding user: %s", user), e);
        }
    }
}
