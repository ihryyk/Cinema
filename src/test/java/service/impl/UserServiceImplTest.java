package service.impl;

import exception.DaoOperationException;
import model.dao.UserDao;
import model.entity.User;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.UserService;
import util.GetEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService userService;

    @Mock
    UserDao userDao;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDao);
    }

    @Test
    void findByPasswordAndEmail() throws DaoOperationException {
        User expected = GetEntity.getUser();
        when(userDao.findByPasswordAndEmail("password", "email")).thenReturn(expected);
        User actual = userService.findByPasswordAndEmail("password", "email");
        assertThat(
                expected,
                allOf(
                        hasProperty("password", CoreMatchers.equalTo(actual.getPassword()))));
    }

    @Test
    void findById() throws DaoOperationException {
        User expected = GetEntity.getUser();
        when(userDao.findById(1L)).thenReturn(expected);
        User actual = userService.findById(1L);
        assertThat(
                expected,
                allOf(
                        hasProperty("password", CoreMatchers.equalTo(actual.getPassword()))));
    }

    @Test
    void findByEmail() throws DaoOperationException {
        User expected = GetEntity.getUser();
        when(userDao.findByEmail("email")).thenReturn(expected);
        User actual = userService.findByEmail("email");
        assertThat(
                expected,
                allOf(
                        hasProperty("emailAddress", CoreMatchers.equalTo(actual.getEmailAddress()))));
    }

    @Test
    void findByPhoneNumber() throws DaoOperationException {
        User expected = GetEntity.getUser();
        when(userDao.findByPhoneNumber("number")).thenReturn(expected);
        User actual = userService.findByPhoneNumber("number");
        assertThat(
                expected,
                allOf(
                        hasProperty("phoneNumber", CoreMatchers.equalTo(actual.getPhoneNumber()))));
    }

    @Test
    void save() throws DaoOperationException {
        User user = GetEntity.getUser();
        doNothing().when(userDao).save(user);
        userService.save(user);
        verify(userDao, times(1)).save(user);
    }

    @Test
    void updateEmail() throws DaoOperationException {
        doNothing().when(userDao).updateEmail("email",1L);
        userService.updateEmail("email",1L);
        verify(userDao, times(1)).updateEmail("email",1L);
    }

    @Test
    void updatePhoneNumber() throws DaoOperationException {
        doNothing().when(userDao).updatePhoneNumber("number",1L);
        userService.updatePhoneNumber("number",1L);
        verify(userDao, times(1)).updatePhoneNumber("number",1L);
    }

    @Test
    void updateContactInformation() throws DaoOperationException {
        User user = GetEntity.getUser();
        doNothing().when(userDao).updateContactInformation(user);
        userService.updateContactInformation(user);
        verify(userDao, times(1)).updateContactInformation(user);
    }
}