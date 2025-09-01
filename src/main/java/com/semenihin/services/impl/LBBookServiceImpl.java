package com.semenihin.services.impl;

import com.semenihin.dao.LBBookMySQLDao;
import com.semenihin.dao.impl.LBBookMySQLDaoImpl;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.exceptions.LBInvalidEntityException;
import com.semenihin.exceptions.LBNotExistException;
import com.semenihin.printer.Printer;
import com.semenihin.printer.impl.LBBookPrinter;
import com.semenihin.services.BookService;
import com.semenihin.services.UserService;
import com.semenihin.validator.impl.LBBookValidator;

import java.util.List;

public class LBBookServiceImpl implements BookService {
    private UserService userService;
    private LBBookMySQLDao bookDao;
    private LBBookValidator bookValidator;
    private Printer<Book> bookPrinter;
    private static LBBookServiceImpl instance;

    public static LBBookServiceImpl getInstance() {
        if (instance == null) {
            instance = new LBBookServiceImpl();
            injectDependencies(instance);
        }
        return instance;
    }

    private LBBookServiceImpl() {}

    private static void injectDependencies(LBBookServiceImpl bookService){
        bookService.bookValidator = LBBookValidator.getInstance();
        bookService.bookDao = LBBookMySQLDaoImpl.getInstance();
        bookService.bookPrinter = LBBookPrinter.getInstance();
        bookService.userService = LBUserServiceImpl.getInstance();
    }

    @Override
    public void createBook(Book book) throws LBFileAccessException {
        if (bookDao.findBook(book.getId()) == null) {
            if (book.getCurrentUser() == null) {
                bookDao.createBook(book);
            } else {
                throw new LBInvalidEntityException("Can't create book that are rented");
            }
        } else {
            throw new LBInvalidEntityException("Book already exist");
        }
    }

    @Override
    public void updateBook(Book book) throws LBFileAccessException {
        if (bookValidator.validate(book)) {
            bookDao.updateBook(book);
        } else {
            throw new LBInvalidEntityException("Book didn't pass validation");
        }
    }

    @Override
    public List<Book> findBooks() {
        return bookDao.findBooks();
    }   

    @Override
    public Book findBook(long id) {
        return bookDao.findBook(id);
    }

    @Override
    public void deleteBook(long id) throws LBFileAccessException {
        boolean isDeleted = false;

        for (Book book : bookDao.findBooks()) {
            if (book.getId() == id) {
                bookDao.delete(findBook(book.getId()));
                isDeleted = true;
            }
        }

        if (!isDeleted){
            throw new LBNotExistException("Book isn't exist and cannot be deleted");
        }
    }

    @Override
    public void printBooks() {
        for (Book book : bookDao.findBooks()) {
            bookPrinter.print(book);
        }
    }

    @Override
    public void rentBook(long bookId, User user) throws LBFileAccessException {
        if (bookDao.findBook(bookId) == null) {
            throw new LBNotExistException("Book not exist");
        }
        if (userService.findUser(user.getId()) == null) {
            throw new LBNotExistException("User not exist");
        }
        bookDao.rentBook(bookId, user);
    }

    @Override
    public void returnBook(long bookId) throws LBFileAccessException {
        if (bookDao.findBook(bookId) == null) {
            throw new LBNotExistException("Book not exist");
        }
        if (bookDao.findBook(bookId).getCurrentUser() == null) {
            throw new LBNotExistException("User not exist");
        }
        bookDao.returnBook(bookId);
    }
}
