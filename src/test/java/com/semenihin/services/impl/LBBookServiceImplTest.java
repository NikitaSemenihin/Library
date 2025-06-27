package com.semenihin.services.impl;

import com.semenihin.dao.BookDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
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
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private UserService userService;

    @Mock
    private BookDao bookDao;

    @Mock
    private LBBookValidator bookValidator;

    @Mock
    private Printer<Book> bookPrinter;

    @InjectMocks
    private LBBookServiceImpl bookService;

    private List<Book> testBooks = new ArrayList<>();

    private Book testBook = new Book(101L, "testik", "testok testovich", 1232, 3210, null);

    private User testUser = new User(404L, "test testovich", "test@test.test", "+3434532");

    @Before
    public void startUp() {
        if (!testBooks.contains(testBook)) {
            testBooks.add(testBook);
        }
        testBook.setCurrentUser(null);
        when(bookDao.getBooks()).thenReturn(testBooks);
        when(bookValidator.validate(testBook)).thenReturn(true);
    }

    @Test
    public void createBook_test() throws Exception {
//        when(bookService.exist(book.getId())).thenReturn(false);
        testBooks.remove(testBook);
        bookService.createBook(testBook);
        verify(bookDao).createBook(testBook);
    }

    @Test
    public void updateBook_test() throws Exception {
        testBook.setCurrentUser(testUser);
        bookService.updateBook(testBook);
        verify(bookDao).updateBook(testBook);
        userService.updateBookInUser(testUser.getId(), testBook.getId());
    }

    @Test
    public void getBooks_test() {
        bookService.getBooks();
        verify(bookDao).getBooks();
    }

    @Test
    public void findBook_test() {
        Book bookCopy = bookService.findBook(testBook.getId());
        assertEquals(bookCopy, testBook);
    }

    @Test
    public void deleteBook_test() throws Exception {
        testBook.setCurrentUser(testUser);
        bookService.deleteBook(testBook.getId());
        verify(userService).returnBook(testUser.getId(), testBook.getId());
        verify(bookDao).delete(testBook);
    }

    @Test
    public void printBooks_test() {
        bookService.printBooks();
        verify(bookPrinter, times(testBooks.size())).print(any(Book.class));
    }

    @Test
    public void rentBook_test() throws Exception {
        bookService.rentBook(testBook.getId(), testUser);
        verify(bookDao).rentBook(testBook.getId(), testUser);
    }

    @Test
    public void returnBook_test() throws Exception {
        bookService.returnBook(testBook.getId());
        verify(bookDao).returnBook(testBook.getId());
    }

    @Test
    public void updateUserInBook_test() throws Exception {
        bookService.updateUserInBook(testBook.getId(), testUser.getId());
        verify(bookDao).updateUserInBook(testBook.getId(), testUser.getId());
    }

    @Test
    public void exist_test() {
        assertTrue(bookService.exist(testBook.getId()));
        assertFalse(bookService.exist(-1L));
    }
}