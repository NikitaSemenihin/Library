package com.semenihin.services.impl;

import com.semenihin.dao.BookDao;
import com.semenihin.dao.impl.BookDaoImpl;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.FileAccessException;
import com.semenihin.exceptions.InvalidEntityException;
import com.semenihin.printer.Printer;
import com.semenihin.printer.impl.BookPrinter;
import com.semenihin.services.BookService;
import com.semenihin.validator.impl.BookValidator;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final BookValidator bookValidator;
    private final Printer<Book> bookPrinter;
    private static BookServiceImpl instance;

    public static BookServiceImpl getInstance() {
        if (instance == null) {
            instance = new BookServiceImpl();
        }
        return instance;
    }

    private BookServiceImpl() {
        this.bookValidator = BookValidator.getInstance();
        this.bookDao = BookDaoImpl.getInstance();
        this.bookPrinter = BookPrinter.getInstance();
    }

    @Override
    public void createBook(Book book) throws FileAccessException {
        if (book.getCurrentUser() == null) {
            bookDao.createBook(book);
        }
        else {
            throw new InvalidEntityException("Can't create book that are rented");
        }
    }

    @Override
    public void updateBook(Book book) throws FileAccessException {
        if (bookValidator.validate(book)) {
            bookDao.updateBook(book);
        }
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Book findBook(long id) {
        for (Book book : bookDao.getBooks()) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void deleteBook(int id) throws FileAccessException {
        for (Book book : bookDao.getBooks()) {
            if (book.getId() == id) {
                bookDao.delete(book);
            }
            else throw new InvalidEntityException("Book isn't exist and cannot be deleted");
        }
    }

    @Override
    public void printBooks() {
        for (Book book : bookDao.getBooks()) {
            bookPrinter.print(book);
        }
    }

    @Override
    public void rentBook(long bookId, User user) throws InvalidEntityException, FileAccessException {
        if (exist(bookId)) {
            bookDao.rentBook(bookId, user);
        }
        else {
            throw new InvalidEntityException("Book not found");
        }
    }

    @Override
    public void returnBook(long bookId) throws InvalidEntityException, FileAccessException {
        if (exist(bookId)) {
            bookDao.returnBook(bookId);
        }
        else {
            throw new InvalidEntityException("Book not found");
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
