package com.semenihin.services.impl;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
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
    private UserDao userDao;

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

        when(userDao.getUsers()).thenReturn(testUsers);
        when(userValidator.validate(testUser)).thenReturn(true);
        when(bookService.exist(testBook.getId())).thenReturn(true);
        when(bookService.findBook(testBook.getId())).thenReturn(testBook);
        when(userDao.findUser(testUser.getId())).thenReturn(testUser);
    }

    @Test
    public void createUserTest() throws Exception {
        testUsers.remove(testUser);
        userService.createUser(testUser);
        verify(userDao).createUser(testUser);
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

    @Test
    public void rentBookTest() throws Exception {
        userService.rentBook(testUser.getId(), testBook.getId());
        verify(bookService).rentBook(testBook.getId(), testUser);
        verify(userDao).rentBook(testUser.getId(), testBook);
    }

    @Test
    public void returnBookTest() throws Exception {
        userService.returnBook(testUser.getId(), testBook.getId());
        verify(bookService).returnBook(testBook.getId());
        verify(userDao).returnBook(testUser.getId(), testBook.getId());
    }

    @Test
    public void existTest() {
        assertTrue(userService.exist(testUser.getId()));
        assertFalse(userService.exist(-1L));
    }

    @Test
    public void updateBookInUserTest() throws Exception {
        userService.updateBookInUser(testUser.getId(), testBook.getId());
        verify(userDao).updateBookInUser(testUser.getId(), testBook.getId());
    }
}