package com.semenihin.services;

import com.semenihin.dao.BookDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.validator.BookValidator;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final BookValidator bookValidator;
    private static BookServiceImpl instance;

    public static BookServiceImpl getInstance() {
        if (instance == null) {
            instance = new BookServiceImpl();
        }
        return instance;
    }

    private BookServiceImpl() {
        this.bookValidator = new BookValidator();
        this.bookDao = BookDao.getInstance();
    }

    public void createBook(int id, String title, String author, int pages, int year, User user) {
        bookDao.createBook(id, title, author, pages, year, user);
    }

    public void updateBook(Book book) {
        if (bookValidator.validate(book)) {
            bookDao.updateBook(book);
        }
    }

    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    public Book getBook(long id) {
        for (Book book : bookDao.getBooks()) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public void deleteBook(int id) {
        for (Book book : bookDao.getBooks()) {
            if (book.getId() == id) {
                bookDao.delete(book);
                break;
            }
        }
    }

    public void printBooks() {
        for (Book book : bookDao.getBooks()) {
            System.out.println("\n\n\n" + book);
        }
    }

    public void rentBook(long bookId, User user) {
        if (bookValidator.exist(bookId)) {
            bookDao.rentBook(bookId, user);
        }
    }

    public void returnBook(long bookId) {
        if (bookValidator.exist(bookId)) {
            bookDao.returnBook(bookId);
        }
    }
}
