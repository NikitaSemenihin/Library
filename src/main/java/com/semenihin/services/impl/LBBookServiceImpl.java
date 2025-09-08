package com.semenihin.services.impl;

import com.semenihin.dao.LBBookMySQLDao;
import com.semenihin.dao.impl.LBBookMySQLDaoImpl;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBDaoException;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.exceptions.LBInvalidEntityException;
import com.semenihin.exceptions.LBNotExistException;
import com.semenihin.printer.Printer;
import com.semenihin.printer.impl.LBBookPrinter;
import com.semenihin.services.BookService;
import com.semenihin.services.UserService;
import com.semenihin.validator.Validator;
import com.semenihin.validator.impl.LBBookValidator;

import java.util.List;
import java.util.Optional;

public class LBBookServiceImpl implements BookService {
    private UserService userService;
    private LBBookMySQLDao bookDao;
    private Validator<Book> bookValidator;
    private Printer<Book> bookPrinter;
    private static LBBookServiceImpl instance;

    public static LBBookServiceImpl getInstance() {
        if (instance == null) {
            instance = new LBBookServiceImpl();
            injectDependencies(instance);
        }
        return instance;
    }

    private LBBookServiceImpl() {
    }

    private static void injectDependencies(LBBookServiceImpl bookService) {
        bookService.bookValidator = LBBookValidator.getInstance();
        bookService.bookDao = LBBookMySQLDaoImpl.getInstance();
        bookService.bookPrinter = LBBookPrinter.getInstance();
        bookService.userService = LBUserServiceImpl.getInstance();
    }

    @Override
    public void createBook(Book book) throws LBDaoException {
        if (bookDao.findBook(book.getId()) != null) {
            throw new LBInvalidEntityException("Book already exist");
        }

        if (book.getCurrentUser() != null) {
            throw new LBInvalidEntityException("Can't create book that are rented");
        }

        bookDao.createBook(book);
    }

    @Override
    public void updateBook(Book book) throws LBDaoException {
        if (bookValidator.validate(book)) {
            throw new LBInvalidEntityException("Book didn't pass validation");
        }

        bookDao.updateBook(book);
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
    public void deleteBook(long id) throws LBDaoException {
        Optional.ofNullable(findBook(id)).ifPresentOrElse(
                book -> bookDao.delete(id),
                () -> {throw new LBNotExistException("Book doesn't exist and cannot be deleted");}
        );
    }

    @Override
    public void rentBook(long bookId, long userId) throws LBDaoException {
        Book book = findBook(bookId);
        if (book == null) {
            throw new LBNotExistException("Book not exist");
        }

        if (book.getCurrentUser() != null) {
            throw new LBInvalidEntityException("Book already rented");
        }

        Optional.ofNullable(userService.findUser(userId)).ifPresentOrElse(
                user -> bookDao.rentBook(bookId, user),
                () -> {throw new LBNotExistException("User not exist");}
        );
    }

    @Override
    public void returnBook(long bookId) throws LBDaoException {
        Book book = findBook(bookId);
        if (book == null) {
            throw new LBNotExistException("Book not exist");
        }

        if (book.getCurrentUser() == null) {
            throw new LBInvalidEntityException("Book not rented");
        }

        bookDao.returnBook(bookId);
    }
}
