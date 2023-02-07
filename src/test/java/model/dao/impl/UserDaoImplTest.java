package model.dao.impl;

import exception.DaoOperationException;
import model.dao.DaoFactory;
import model.dao.TicketDao;
import model.dao.UserDao;
import model.entity.User;
import model.enums.MovieFormat;
import model.enums.UserRole;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DatabaseBuildScript;
import util.GetEntity;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private final static UserDao userDao = DaoFactory.getUserDao();

    @BeforeEach
    public void runScript() {
        try {
            DatabaseBuildScript.RunSqlScript();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findByPasswordAndEmail() throws DaoOperationException {
        User actual = userDao.findByPasswordAndEmail("password","email_address");
        assertThat(
                actual,
                allOf(
                        hasProperty("password", CoreMatchers.equalTo("password")),
                        hasProperty("emailAddress", CoreMatchers.equalTo("email_address"))));
    }

    @Test
    void findById() throws DaoOperationException {
        User actual = userDao.findById(2L);
        assertThat(
                actual,
                allOf(
                        hasProperty("password", CoreMatchers.equalTo("password")),
                        hasProperty("emailAddress", CoreMatchers.equalTo("email_address"))));
    }

    @Test
    void findByEmail() throws DaoOperationException {
        User actual = userDao.findByEmail("email_address");
        assertThat(
                actual,
                allOf(hasProperty("emailAddress", CoreMatchers.equalTo("email_address"))));
    }

    @Test
    void findByPhoneNumber() throws DaoOperationException {
        User actual = userDao.findByPhoneNumber("phone_number");
        assertThat(
                actual,
                allOf(hasProperty("phoneNumber", CoreMatchers.equalTo("phone_number"))));
    }

    @Test
    void updateEmail() throws DaoOperationException {
        userDao.updateEmail("email",2L);
        User actual = userDao.findByEmail("email");
        assertThat(
                actual,
                allOf(hasProperty("emailAddress", CoreMatchers.equalTo(actual.getEmailAddress()))));
    }

    @Test
    void updatePhoneNumber() throws DaoOperationException {
        userDao.updatePhoneNumber("phone",2L);
        User actual = userDao.findByPhoneNumber("phone");
        assertThat(
                actual,
                allOf(hasProperty("phoneNumber", CoreMatchers.equalTo(actual.getPhoneNumber()))));
    }

    @Test
    void updateContactInformation() throws DaoOperationException {
        User user = GetEntity.getUser();
        user.setId(2L);
        userDao.updateContactInformation(user);
        User actual = userDao.findByPhoneNumber("phone_number");
        assertThat(
                actual,
                allOf(hasProperty("firstName", CoreMatchers.equalTo(actual.getFirstName()))));
    }

    @Test
    void save() throws DaoOperationException {
        User user = GetEntity.getUser();
        userDao.save(user);
        User actual = userDao.findByPhoneNumber("phone2");
        assertThat(
                actual,
                allOf(hasProperty("firstName", CoreMatchers.equalTo(actual.getFirstName()))));
    }


}