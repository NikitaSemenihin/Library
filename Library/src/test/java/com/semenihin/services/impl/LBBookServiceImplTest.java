package com.semenihin.services.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.exceptions.LBInvalidEntityException;
import com.semenihin.exceptions.LBNotExistException;
import com.semenihin.services.BookService;

import com.semenihin.services.UserService;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



class LBBookServiceImplTest {
    private static BookService bookService;
    private static UserService userService;

    @BeforeAll
    static void setUp() {
        bookService = LBBookServiceImpl.getInstance();
        userService = LBUserServiceImpl.getInstance();
    }

    @AfterAll
    static void tearDown() throws LBFileAccessException {
        Book book = bookService.findBook(101L);
        if (book != null) bookService.deleteBook(book.getId());

        Book book1 = bookService.findBook(202L);
        if (book1 != null) bookService.deleteBook(book1.getId());

        User user = userService.findUser(101L);
        if (user != null) userService.deleteUser(101L);
    }

    @Test
    @Order(1)
    void createBook_shouldAddNewBook() throws LBFileAccessException {
        Book book = new Book(101L, "Test Book", "Test Test", 132, 321, null);

        bookService.createBook(book);
        Book found = bookService.findBook(101L);

        assertNotNull(found);
        assertEquals("Test Book", found.getTitle());
    }

    @Test
    @Order(2)
    void createBook_shouldThrowIfBookExist() throws LBFileAccessException {
        Book book = new Book(101L, "Test Book", "Test Test", 132, 321, null);
        bookService.createBook(book);

        Book duplicate = new Book(101L, "Test Book", "Test Test", 132, 321, null);

        assertThrows(LBInvalidEntityException.class, () -> bookService.createBook(duplicate));
    }

    @Test
    @Order(3)
    void updateBook_shouldUpdateTitle() throws LBFileAccessException {
        Book updated = new Book(101L, "Test Book updated", "Test Test", 132, 321, null);

        bookService.updateBook(updated);
        Book found = bookService.findBook(101L);

        assertNotNull(found);
        assertEquals("Test Book updated", found.getTitle());
    }

    @Test
    @Order(4)
    void rentBook_shouldAssignUserToBook() throws LBFileAccessException {
        User user = new User(101L, "Test User", "test@emmail.com", "+39459340");
        userService.createUser(user);
        bookService.rentBook(101L, user);

        Book rented = bookService.findBook(101L);
        assertNotNull(rented.getCurrentUser());
        assertEquals("Test User", rented.getCurrentUser().getFullName());
    }

    @Test
    @Order(5)
    void returnBook_shouldClearUserFromBook() throws LBFileAccessException {
        bookService.returnBook(101L);
        Book returned = bookService.findBook(101L);

        assertNull(returned.getCurrentUser());
    }

    @Test
    @Order(6)
    void deleteBook_ShouldRemoveBook() throws LBFileAccessException {
        bookService.deleteBook(101L);
        Book deleted = bookService.findBook(101L);

        assertNull(deleted);
    }

    @Test
    @Order(7)
    void deleteBook_shouldThrowIfNotExist() {
        assertThrows(LBNotExistException.class, () -> bookService.deleteBook(999));
    }

    @Test
    void exist_shouldReturnTrueIfExist() throws LBFileAccessException {
        Book book = new Book(202L, "Exist Test Book",
                "Exist Test Author", 123, 321, null);
        bookService.createBook(book);

        assertTrue(bookService.exist(202L));
    }

    @Test
    void getBooks_shouldReturnList() {
        List<Book> books = bookService.getBooks();
        assertNotNull(books);
    }
}