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

    @Mock
    private LBUserFileWriter userFileWriter;

    @Mock
    private BookService bookService;

    @Spy
    private List<User> users = new ArrayList<>();

    @Spy
    private User testUser = new User(4L, "test testovich", "test@test.test", "+3434532");

    private User testUSer_notSpy = new User(5L, "test testovich",
            "test@test.test", "+3434532");

    @Spy
    private Book testBook = new Book(1L, "testik", "testok testovich", 1232, 3210, null);

    @InjectMocks
    private LBUserDaoImpl userDao;


    @Before
    public void setUp() {
        if (!users.contains(testUser)) {
            users.add(testUser);
        }
        if (!users.contains(testUSer_notSpy)) {
            users.add(testUSer_notSpy);
        }
        testUser.getRentedBooks().remove(testBook);
    }

    @Test
    public void getUsers_test() {
        if (users.size() == userDao.getUsers().size()) {
            for (int i = 0; i < users.size(); i++) {
                assertEquals(users.get(i), userDao.getUsers().get(i));
            }
        } else {
            assertEquals(users.size(), userDao.getUsers().size());
        }

    }

    @Test
    public void rentBook_test() {
        userDao.rentBook(testUser.getId(), testBook);
        verify(testUser, times(2)).getRentedBooks();
    }

    @Test
    public void returnBook_test() {
        userDao.returnBook(testUser.getId(), testBook.getId());
        verify(testUser, times(2)).getRentedBooks();
    }

    @Test
    public void updateUser_test() throws Exception {
        testUser.getRentedBooks().add(testBook);
        userDao.updateUser(testUser);
        verify(userFileWriter).update(users);
        verify(bookService).updateUserInBook(testBook.getId(), testUser.getId());
    }

    @Test
    public void findUser_test() {
        assertEquals(testUSer_notSpy, userDao.findUser(testUSer_notSpy.getId()));
    }

    @Test
    public void createUser_test() throws Exception {
        if (users.contains(testUser)) {
            users.remove(testUser);
        }
        userDao.createUser(testUser);
        verify(userFileWriter).update(users);
    }

    @Test
    public void deleteUser_test() throws Exception {
        userDao.deleteUser(testUser.getId());
        verify(userFileWriter).update(users);
    }

    @Test
    public void updateBookInUser_test() throws Exception {
        userDao.updateBookInUser(testUser.getId(), testBook.getId());
        verify(userFileWriter).update(users);
    }
}