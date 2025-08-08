package com.semenihin.dao.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.fileReader.impl.LBBookFileReader;
import com.semenihin.fileWriter.FileWriterInterface;

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

public class LBBookDaoImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static final Long TEST_BOOK_ID = 9999L;
    private static final String TEST_BOOK_TITLE = "test";
    private static final String TEST_BOOK_AUTHOR = "test test";
    private static final int TEST_BOOK_PAGES = 123;
    private static final int TEST_BOOK_YEAR = 321;

    @Mock
    private FileWriterInterface<Book> bookFileWriter;

    @Mock
    private LBBookFileReader bookFileReader;

    @Mock
    private User testUser;

    @Spy
    private List<Book> spyBooks = new ArrayList<>();

    @Spy
    private Book spyBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_BOOK_AUTHOR,
            TEST_BOOK_PAGES, TEST_BOOK_YEAR, null);;

    @InjectMocks
    private LBBookDaoImpl bookDao;

    @Before
    public void setUp() throws LBFileAccessException {
//        List<Book> books = new ArrayList<>();

        when(bookFileReader.readEntitiesFromFile()).thenReturn(spyBooks);

//        spyBooks = spy(books);
//        spyBook = spy(book);
    }

    @Test
    public void createBookTest() throws Exception {
        spyBooks.add(spyBook);
        bookDao.createBook(spyBook);
        verify(spyBook).clone();
        verify(bookFileWriter).update(spyBooks);
    }

    @Test(expected = LBFileAccessException.class)
    public void createBookExceptionTest() throws LBFileAccessException, FileNotFoundException {
        doThrow(new LBFileAccessException("File not found")).when(bookFileWriter).update(spyBooks);
        bookDao.createBook(spyBook);
    }

    @Test
    public void updateBookTest() throws Exception {
        bookDao.updateBook(spyBook);
        verify(bookFileWriter).update(spyBooks);
    }

    @Test(expected = LBFileAccessException.class)
    public void updateBookExceptionTest() throws LBFileAccessException, FileNotFoundException {
        doThrow(new LBFileAccessException("File not found")).when(bookFileWriter).update(anyList());
        bookDao.updateBook(spyBook);
    }

    @Test
    public void deleteBookTest() throws Exception {
        bookDao.delete(spyBook);
        verify(bookFileWriter).update(spyBooks);
    }

    @Test(expected = LBFileAccessException.class)
    public void deleteBookExceptionTest() throws LBFileAccessException, FileNotFoundException {
        doThrow(new LBFileAccessException("File not found")).when(bookFileWriter).update(anyList());
        bookDao.delete(spyBook);
    }

    @Test
    public void getBooksTest() {
        List<Book> copy = bookDao.getBooks();
        assertEquals(copy, spyBooks);
    }

    @Test
    public void rentBookTest() throws Exception {
        bookDao.rentBook(spyBook.getId(), testUser);
        verify(bookFileWriter).update(spyBooks);
    }

    @Test
    public void returnBookTest() throws Exception {
        bookDao.returnBook(spyBook.getId());
        verify(bookFileWriter).update(spyBooks);
    }

    @Test
    public void findBookTest() {
        doReturn(spyBook).when(bookDao).findBook(spyBook.getId());
        assertEquals(spyBook, bookDao.findBook(spyBook.getId()));
    }

    @Test
    public void updateUserInBookTest() throws Exception {
        bookDao.updateUserInBook(spyBook.getId(), testUser.getId());
        verify(bookFileWriter).update(spyBooks);
    }
}