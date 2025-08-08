package com.semenihin.dao.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.fileWriter.impl.LBUserFileWriter;
import com.semenihin.services.BookService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LBUserDaoImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static final Long TEST_BOOK_ID = 9999L;
    private static final String TEST_BOOK_TITLE = "test";
    private static final String TEST_BOOK_AUTHOR = "test test";
    private static final int TEST_BOOK_PAGES = 123;
    private static final int TEST_BOOK_YEAR = 321;
    private static final Long TEST_USER_ID = 101L;
    private static final Long NOT_SPY_TEST_USER_ID = 102L;
    private static final String TEST_USER_FULL_NAME = "user";
    private static final String TEST_USER_EMAIL = "test@email.com";
    private static final String TEST_USER_PHONE_NUMBER = "+3242543224";

    @Mock
    private LBUserFileWriter userFileWriter;

    @Mock
    private BookService bookService;

    @Spy
    private List<User> users;

    @Spy
    private User testUser;

    private User testUSer_notSpy;

    @Spy
    private Book testBook;

    @InjectMocks
    private LBUserDaoImpl userDao;


    @Before
    public void setUp() {
        users = new ArrayList<>();
        testUser = new User(TEST_USER_ID, TEST_USER_FULL_NAME, TEST_USER_EMAIL, TEST_USER_PHONE_NUMBER);
        testUSer_notSpy = new User(NOT_SPY_TEST_USER_ID, TEST_USER_FULL_NAME,TEST_USER_EMAIL, TEST_USER_PHONE_NUMBER);
        testBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_BOOK_AUTHOR, TEST_BOOK_PAGES, TEST_BOOK_YEAR, null);
        users.add(testUser);
        users.add(testUSer_notSpy);

        testUser.getRentedBooks().remove(testBook);
    }

    @Test
    public void getUsersTest() {
        if (users.size() == userDao.getUsers().size()) {
            for (int i = 0; i < users.size(); i++) {
                assertEquals(users.get(i), userDao.getUsers().get(i));
            }
        } else {
            assertEquals(users.size(), userDao.getUsers().size());
        }

    }

    @Test
    public void rentBookTest() {
        userDao.rentBook(testUser.getId(), testBook);
        verify(testUser, times(2)).getRentedBooks();
    }

    @Test
    public void returnBookTest() {
        userDao.returnBook(testUser.getId(), testBook.getId());
        verify(testUser, times(2)).getRentedBooks();
    }

    @Test
    public void updateUserTest() throws Exception {
        testUser.getRentedBooks().add(testBook);
        userDao.updateUser(testUser);
        verify(userFileWriter).update(users);
        verify(bookService).updateUserInBook(testBook.getId(), testUser.getId());
    }

    @Test
    public void findUserTest() {
        assertEquals(testUSer_notSpy, userDao.findUser(testUSer_notSpy.getId()));
    }

    @Test
    public void createUserTest() throws Exception {
        if (users.contains(testUser)) {
            users.remove(testUser);
        }
        userDao.createUser(testUser);
        verify(userFileWriter).update(users);
    }

    @Test
    public void deleteUserTest() throws Exception {
        userDao.deleteUser(testUser.getId());
        verify(userFileWriter).update(users);
    }

    @Test
    public void updateBookInUserTest() throws Exception {
        userDao.updateBookInUser(testUser.getId(), testBook.getId());
        verify(userFileWriter).update(users);
    }
}