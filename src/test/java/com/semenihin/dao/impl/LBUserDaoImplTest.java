package com.semenihin.dao.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.exceptions.LBNotExistException;
import com.semenihin.fileReader.impl.LBUserFileReader;
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

import java.io.FileNotFoundException;
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
    private LBUserFileReader userFileReader;

    @Mock
    private LBUserFileWriter userFileWriter;

    @Mock
    private BookService bookService;

    @Spy
    private List<User> spyUsers = new ArrayList<>();

    @Spy
    private User spyUser = new User(TEST_USER_ID, TEST_USER_FULL_NAME, TEST_USER_EMAIL, TEST_USER_PHONE_NUMBER);

    @Spy
    private Book spyBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_BOOK_AUTHOR,
            TEST_BOOK_PAGES, TEST_BOOK_YEAR, null);

    @InjectMocks
    private LBUserDaoImpl userDao;

    private final User testUSer_notSpy = new User(NOT_SPY_TEST_USER_ID,
            TEST_USER_FULL_NAME,TEST_USER_EMAIL, TEST_USER_PHONE_NUMBER);


    @Before
    public void setUp() throws LBFileAccessException {
        when(userFileReader.readEntitiesFromFile()).thenReturn(spyUsers);

        spyUsers.add(spyUser);
        spyUsers.add(testUSer_notSpy);

        spyUser.getRentedBooks().remove(spyBook);
    }

    @Test
    public void getUsersTest() {
        if (spyUsers.size() == userDao.getUsers().size()) {
            for (int i = 0; i < spyUsers.size(); i++) {
                assertEquals(spyUsers.get(i), userDao.getUsers().get(i));
            }
        } else {
            assertEquals(spyUsers.size(), userDao.getUsers().size());
        }

    }

    @Test
    public void rentBookTest() {
        userDao.rentBook(spyUser.getId(), spyBook);
        verify(spyUser, times(2)).getRentedBooks();
    }

    @Test
    public void returnBookAllPassTest() {
        userDao.returnBook(spyUser.getId(), spyBook.getId());
        verify(spyUser, times(2)).getRentedBooks();
    }

    @Test(expected = LBNotExistException.class)
    public void returnBookExceptionTest() {
        userDao.returnBook(11111, spyBook.getId());
    }

    @Test
    public void updateUserTest() throws Exception {
        spyUser.getRentedBooks().add(spyBook);
        userDao.updateUser(spyUser);
        verify(userFileWriter).update(spyUsers);
        verify(bookService).updateUserInBook(spyBook.getId(), spyUser.getId());
    }

    @Test
    public void findUserTest() {
        assertEquals(testUSer_notSpy, userDao.findUser(testUSer_notSpy.getId()));
    }

    @Test
    public void createUserTest() throws Exception {
        if (spyUsers.contains(spyUser)) {
            spyUsers.remove(spyUser);
        }
        userDao.createUser(spyUser);
        verify(userFileWriter).update(spyUsers);
    }

    @Test
    public void deleteUserTest() throws Exception {
        userDao.deleteUser(spyUser.getId());
        verify(userFileWriter).update(spyUsers);
    }

    @Test
    public void updateBookInUserTest() throws Exception {
        userDao.updateBookInUser(spyUser.getId(), spyBook.getId());
        verify(userFileWriter).update(spyUsers);
    }
}