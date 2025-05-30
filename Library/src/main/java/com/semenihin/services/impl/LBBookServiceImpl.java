package com.semenihin.services.impl;

import com.semenihin.dao.BookDao;
import com.semenihin.dao.impl.LBBookDaoImpl;
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
    private BookDao bookDao;
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
        bookService.bookDao = LBBookDaoImpl.getInstance();
        bookService.bookPrinter = LBBookPrinter.getInstance();
        bookService.userService = LBUserServiceImpl.getInstance();
    }

    @Override
    public void createBook(Book book) throws LBFileAccessException {
        if (!exist(book.getId())){
            if (book.getCurrentUser() == null) {
                bookDao.createBook(book);
            } else {
                throw new LBInvalidEntityException("Can't create book that are rented");
            }
        }
        else throw new LBInvalidEntityException("Book already exist");
    }

    @Override
    public void updateBook(Book book) throws LBFileAccessException {
        if (bookValidator.validate(book)) {
            bookDao.updateBook(book);
            userService.updateBookInUser(book.getCurrentUser().getId(), book.getId());
        }
        else throw new LBInvalidEntityException("Book didn't pass validation");
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
    public void deleteBook(int id) throws LBFileAccessException {
        boolean isDeleted = false;

        for (Book book : bookDao.getBooks()) {
            if (book.getId() == id) {
                if (book.getCurrentUser() != null){
                    userService.returnBook(book.getCurrentUser().getId(), book.getId());
                }
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
        for (Book book : bookDao.getBooks()) {
            bookPrinter.print(book);
        }
    }

    @Override
    public void rentBook(long bookId, User user) throws LBFileAccessException {
        if (exist(bookId)) {
            bookDao.rentBook(bookId, user);
        }
        else {
            throw new LBNotExistException("Book not exist");
        }
    }

    @Override
    public void returnBook(long bookId) throws LBFileAccessException {
        if (exist(bookId)) {
            bookDao.returnBook(bookId);
        }
        else {
            throw new LBNotExistException("Book not exist");
        }
    }

    @Override
    public void updateUserInBook(long bookID, long userID) throws LBFileAccessException {
        bookDao.updateUserInBook(bookID, userID);
    }

    @Override
    public boolean exist(long bookId) {
        for (Book selectedBook : bookDao.getBooks()) {
            if (selectedBook.getId() == bookId) {
                return true;
            }
        }
        return false;
    }
}
