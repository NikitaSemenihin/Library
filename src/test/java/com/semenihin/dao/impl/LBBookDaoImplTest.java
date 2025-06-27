package com.semenihin.dao.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.fileWriter.FileWriterInterface;
import com.semenihin.services.UserService;

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

public class LBBookDaoImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private FileWriterInterface<Book> bookFileWriter;

    @Mock
    private UserService userService;

    @Spy
    private List<Book> books = new ArrayList<>();

    private Book book = new Book(1, "testik", "testok testovich", 1232, 3210, null);

    @InjectMocks
    private LBBookDaoImpl bookDao;

    @Mock
    private User testUser;

    @Before
    public void startUp() {
        if (!books.contains(book)) {
            books.add(book);
        }
        book.setCurrentUser(null);
    }

    @Test
    public void createBook_test() throws Exception {
        bookDao.createBook(book);
        verify(bookFileWriter).update(books);
    }

    @Test
    public void updateBook_test() throws Exception {
        bookDao.updateBook(book);
        verify(bookFileWriter).update(books);
    }

    @Test
    public void deleteBook_test() throws Exception {
        bookDao.delete(book);
        verify(bookFileWriter).update(books);
    }

    @Test
    public void rentBook_test() throws Exception {
        bookDao.rentBook(book.getId(), testUser);
        verify(bookFileWriter).update(books);
    }

    @Test
    public void returnBook_test() throws Exception {
        bookDao.returnBook(book.getId());
        verify(bookFileWriter).update(books);
    }

    @Test
    public void findBook_test() {
        assertEquals(book, bookDao.findBook(book.getId()));
    }

    @Test
    public void updateUserInBook_test() throws Exception {
        bookDao.updateUserInBook(book.getId(), testUser.getId());
        verify(bookFileWriter).update(books);
    }
}