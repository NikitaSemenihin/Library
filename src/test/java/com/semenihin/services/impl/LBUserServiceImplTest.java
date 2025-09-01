package com.semenihin.services.impl;

import com.semenihin.dao.LBUserMySQLDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.exceptions.LBInvalidEntityException;
import com.semenihin.exceptions.LBNotExistException;
import com.semenihin.printer.Printer;
import com.semenihin.validator.Validator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LBUserServiceImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static final Long TEST_BOOK_ID = 9999L;
    private static final String TEST_BOOK_TITLE = "test";
    private static final String TEST_BOOK_AUTHOR = "test test";
    private static final int TEST_BOOK_PAGES = 123;
    private static final int TEST_BOOK_YEAR = 321;
    private static final Long TEST_USER_ID = 101L;
    private static final String TEST_USER_FULL_NAME = "user";
    private static final String TEST_USER_EMAIL = "test@email.com";
    private static final String TEST_USER_PHONE_NUMBER = "+3242543224";

    @Mock
    private LBUserMySQLDao userDao;

    @Mock
    private LBBookServiceImpl bookService;

    @Mock
    private Validator<User> userValidator;

    @Mock
    private Printer<User> userPrinter;

    @InjectMocks
    private LBUserServiceImpl userService;

    private List<User> testUsers;

    private User testUser;

    private Book testBook;

    @Before
    public void setUp() {
        testUsers = new ArrayList<>();
        testBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_BOOK_AUTHOR, TEST_BOOK_PAGES, TEST_BOOK_YEAR, null);
        testUser = new User(TEST_USER_ID, TEST_USER_FULL_NAME, TEST_USER_EMAIL, TEST_USER_PHONE_NUMBER);
        testUsers.add(testUser);

        when(userDao.findUsers()).thenReturn(testUsers);
        when(userValidator.validate(testUser)).thenReturn(true);
        when(bookService.findBook(testBook.getId())).thenReturn(testBook);
        when(userDao.findUser(testUser.getId())).thenReturn(testUser);
    }

    @Test
    public void createUserAllPassTest() throws Exception {
        testUsers.remove(testUser);
        userService.createUser(testUser);
        verify(userDao).createUser(testUser);
    }

    @Test(expected = LBInvalidEntityException.class)
    public void createUserValidateExceptionTest() throws LBFileAccessException {
        when(userValidator.validate(testUser)).thenReturn(false);
        userService.createUser(testUser);
    }

    @Test(expected = LBInvalidEntityException.class)
    public void createUserExistExceptionTest() throws LBFileAccessException {
        userService.createUser(testUser);
    }

    @Test
    public void findUserTest() {
        User userCopy = userService.findUser(testUser.getId());
        assertEquals(userCopy, testUser);
    }

    @Test
    public void printUsersTest() {
        userService.printUsers();
        verify(userPrinter, times(testUsers.size())).print(any(User.class));
    }

    @Test
    public void deleteUserTest() throws Exception {
        userService.deleteUser(testUser.getId());
        verify(userDao).deleteUser(testUser.getId());
    }

    @Test(expected = LBNotExistException.class)
    public void deleteUserExistExceptionTest() throws LBFileAccessException {
        testUsers.remove(testUser);
        userService.deleteUser(testUser.getId());
    }
}