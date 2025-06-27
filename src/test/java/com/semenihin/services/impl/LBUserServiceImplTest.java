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

    private List<User> testUsers = new ArrayList<>();

    private User testUser = new User(404L, "test testovich", "test@test.test", "+3434532");

    private Book testBook = new Book(101L, "testik", "testok testovich", 1232, 3210, null);

    @Before
    public void setUp() {
        if (!testUsers.contains(testUser)) {
            testUsers.add(testUser);
        }
        when(userDao.getUsers()).thenReturn(testUsers);
        when(userValidator.validate(testUser)).thenReturn(true);
        when(bookService.exist(testBook.getId())).thenReturn(true);
        when(bookService.findBook(testBook.getId())).thenReturn(testBook);
        when(userDao.findUser(testUser.getId())).thenReturn(testUser);
    }

    @Test
    public void createUser_test() throws Exception {
        testUsers.remove(testUser);
        userService.createUser(testUser);
        verify(userDao).createUser(testUser);
    }

    @Test
    public void findUser_test() {
        User userCopy = userService.findUser(testUser.getId());
        assertEquals(userCopy, testUser);
    }

    @Test
    public void printUsers_test() {
        userService.printUsers();
        verify(userPrinter, times(testUsers.size())).print(any(User.class));
    }

    @Test
    public void deleteUser_test() throws Exception {
        userService.deleteUser(testUser.getId());
        verify(userDao).deleteUser(testUser.getId());
    }

    @Test
    public void rentBook_test() throws Exception {
        userService.rentBook(testUser.getId(), testBook.getId());
        verify(bookService).rentBook(testBook.getId(), testUser);
        verify(userDao).rentBook(testUser.getId(), testBook);
    }

    @Test
    public void returnBook_test() throws Exception {
        userService.returnBook(testUser.getId(), testBook.getId());
        verify(bookService).returnBook(testBook.getId());
        verify(userDao).returnBook(testUser.getId(), testBook.getId());
    }

    @Test
    public void exist_test() {
        assertTrue(userService.exist(testUser.getId()));
        assertFalse(userService.exist(-1L));
    }

    @Test
    public void updateBookInUser_test() throws Exception {
        userService.updateBookInUser(testUser.getId(), testBook.getId());
        verify(userDao).updateBookInUser(testUser.getId(), testBook.getId());
    }
}