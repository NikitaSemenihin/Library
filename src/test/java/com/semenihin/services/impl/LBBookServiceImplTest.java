package com.semenihin.services.impl;

import com.semenihin.dao.LBBookMySQLDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBInvalidEntityException;
import com.semenihin.exceptions.LBNotExistException;
import com.semenihin.printer.Printer;
import com.semenihin.services.UserService;
import com.semenihin.validator.impl.LBBookValidator;
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
import static org.mockito.Mockito.*;

public class LBBookServiceImplTest {
    private static final Long TEST_BOOK_ID = 9999L;
    private static final String TEST_BOOK_TITLE = "test";
    private static final String TEST_BOOK_AUTHOR = "test test";
    private static final int TEST_BOOK_PAGES = 123;
    private static final int TEST_BOOK_YEAR = 321;
    private static final Long TEST_USER_ID = 101L;
    private static final String TEST_USER_FULL_NAME = "user";
    private static final String TEST_USER_EMAIL = "test@email.com";
    private static final String TEST_USER_PHONE_NUMBER = "+3242543224";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private UserService userService;

    @Mock
    private LBBookMySQLDao bookDao;

    @Mock
    private LBBookValidator bookValidator;

    @Mock
    private Printer<Book> bookPrinter;

    @InjectMocks
    private LBBookServiceImpl bookService;

    private List<Book> testBooks = new ArrayList<>();
    private Book testBook;
    private User testUser;

    @Before
    public void setUp() {
        testBooks = new ArrayList<>();
        testBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_BOOK_AUTHOR, TEST_BOOK_PAGES, TEST_BOOK_YEAR, null);
        testUser = new User(TEST_USER_ID, TEST_USER_FULL_NAME, TEST_USER_EMAIL, TEST_USER_PHONE_NUMBER);
        testBooks.add(testBook);

        when(bookDao.findBooks()).thenReturn(testBooks);
        when(bookValidator.validate(testBook)).thenReturn(true);
    }

    @Test
    public void createBookTest() throws Exception {
        when(bookDao.findBooks()).thenReturn(List.of());

        bookService.createBook(testBook);
        verify(bookDao).createBook(testBook);
    }

    @Test(expected = LBInvalidEntityException.class)
    public void createBookTest_WhenBookAreRented() throws Exception {
        when(bookDao.findBooks()).thenReturn(List.of());
        testBook.setCurrentUser(testUser);

        bookService.createBook(testBook);
    }

    @Test(expected = LBInvalidEntityException.class)
    public void createBookTest_WhenBookAlreadyExist() throws Exception {
        bookService.createBook(testBook);
    }

    @Test
    public void updateBookTest() throws Exception {
        testBook.setCurrentUser(testUser);
        bookService.updateBook(testBook);
        verify(bookDao).updateBook(testBook);
    }

    @Test(expected = LBInvalidEntityException.class)
    public void updateBookExceptionTest() throws Exception {
        when(bookValidator.validate(testBook)).thenReturn(false);
        bookService.updateBook(testBook);
    }

    @Test
    public void findBooksTest() {
        bookService.findBooks();
        verify(bookDao).findBooks();
    }

    @Test
    public void findBookTest() {
        Book bookCopy = bookService.findBook(testBook.getId());
        assertEquals(bookCopy, testBook);
    }

    @Test
    public void deleteBookTest() throws Exception {
        testBook.setCurrentUser(testUser);
        bookService.deleteBook(testBook.getId());
        verify(bookDao).delete(testBook.getId());
    }

    @Test(expected = LBNotExistException.class)
    public void deleteBookExceptionTest() throws Exception {
        bookService.deleteBook(929292);
    }

    @Test
    public void rentBookTest() throws Exception {
        bookService.rentBook(testBook.getId(), testUser.getId());
        verify(bookDao).rentBook(testBook.getId(), testUser);
    }

    @Test(expected = LBNotExistException.class)
    public void rentBookExceptionTest() throws Exception {
        bookService.rentBook(929292, testUser.getId());
    }

    @Test
    public void returnBookTest() throws Exception {
        bookService.returnBook(testBook.getId());
        verify(bookDao).returnBook(testBook.getId());
    }

    @Test(expected = LBNotExistException.class)
    public void returnBookExceptionTest() throws Exception {
        bookService.returnBook(929292);
    }
}