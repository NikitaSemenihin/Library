package com.semenihin.services.impl;

import com.semenihin.dao.BookDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.DaoCrashException;
import com.semenihin.exceptions.ServiceCrashExeption;
import com.semenihin.services.BookService;
import com.semenihin.validator.impl.BookValidator;

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
        this.bookValidator = BookValidator.getInstance();
        try {
            this.bookDao = BookDao.getInstance();
        } catch (DaoCrashException e) {
            throw new ServiceCrashExeption(e);
        }

    }

    @Override
    public void createBook(int id, String title, String author, int pages, int year, User user) {
        bookDao.createBook(id, title, author, pages, year, user);
    }

    @Override
    public void updateBook(Book book) {
        if (bookValidator.validate(book)) {
            bookDao.updateBook(book);
        }
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Book getBook(long id) {
        for (Book book : bookDao.getBooks()) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void deleteBook(int id) {
        for (Book book : bookDao.getBooks()) {
            if (book.getId() == id) {
                bookDao.delete(book);
                break;
            }
        }
    }

    @Override
    public void printBooks() {
        for (Book book : bookDao.getBooks()) {
            System.out.println("\n\n\n" + book);
        }
    }

    @Override
    public void rentBook(long bookId, User user) {
        if (exist(bookId)) {
            bookDao.rentBook(bookId, user);
        }
    }

    @Override
    public void returnBook(long bookId) {
        if (exist(bookId)) {
            bookDao.returnBook(bookId);
        }
    }

    public boolean exist(Book book) {
        for (Book selectedBook : bookDao.getBooks()) {
            if (selectedBook.equals(book)) {
                return true;
            }
        }
        return false;
    }

    public boolean exist(long bookId) {
        for (Book selectedBook : bookDao.getBooks()) {
            if (selectedBook.getId() == bookId) {
                return true;
            }
        }
        return false;
    }
}
