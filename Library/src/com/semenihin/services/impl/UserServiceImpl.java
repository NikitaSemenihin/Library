package com.semenihin.services.impl;

import com.semenihin.dao.UserDao;
import com.semenihin.dao.impl.UserDaoImpl;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.FileAccessException;
import com.semenihin.exceptions.InvalidEntityException;
import com.semenihin.printer.Printer;
import com.semenihin.printer.impl.UserPrinter;
import com.semenihin.services.UserService;
import com.semenihin.validator.impl.UserValidator;
import com.semenihin.validator.Validator;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private static UserServiceImpl instance;
    private final BookServiceImpl bookService;
    private final Validator<User> userValidator;
    private final Printer<User> userPrinter;


    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private UserServiceImpl() {
        this.userValidator = UserValidator.getInstance();
        this.userDao = UserDaoImpl.getInstance();
        this.bookService = BookServiceImpl.getInstance();
        this.userPrinter = UserPrinter.getInstance();
    }

    @Override
    public void createUser(User user) throws InvalidEntityException, FileAccessException {
        if (!userValidator.validate(user)) {
            throw new InvalidEntityException("Incorrect fields");
        }
        if (exist(user)) {
            throw new InvalidEntityException("User already exist");
        }
        userDao.createUser(user);
    }

    @Override
    public User findUser(long userId) {
        return userDao.findUser(userId);
    }

    @Override
    public void printUsers() {
        for (User user : userDao.getUsers()) {
            userPrinter.print(user);
        }
    }

    @Override
    public void deleteUser(long userId) throws FileAccessException {
        if (!exist(userId)) {
            throw new InvalidEntityException("User not exist");
        }
        userDao.deleteUser(userId);
    }

    @Override
    public void rentBook(User user, Book book) throws FileAccessException {
        if (!exist(user)) {
            throw new InvalidEntityException("User not exist");
        }
        if (!bookService.exist(book)){
            throw new InvalidEntityException("Book not exist");
        }
        try {
            bookService.rentBook(book.getId(), user);
        } catch (FileAccessException e) {
            throw new RuntimeException(e);
        }
        userDao.rentBook(user.getId(), book);
    }

    @Override
    public void returnBook(User user, Book book) throws FileAccessException {
        if (exist(user)) {
            throw new InvalidEntityException("User not exist");
        }
        if (bookService.exist(book)) {
            throw new InvalidEntityException("Book not exist");
        }
        try {
            bookService.returnBook(book.getId());
        } catch (FileAccessException e) {
            throw new RuntimeException(e);
        }
        userDao.returnBook(user, book);
    }

    public boolean exist(User user) {
        for (User selectedUser : userDao.getUsers()) {
            if (selectedUser.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public boolean exist(long userId) {
        for (User selectedUser : userDao.getUsers()) {
            if (selectedUser.getId() == userId) {
                return true;
            }
        }
        return false;
    }
}