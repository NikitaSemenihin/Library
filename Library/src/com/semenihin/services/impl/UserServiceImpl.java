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
    private UserDao userDao;
    private static UserServiceImpl instance;
    private BookServiceImpl bookService;
    private Validator<User> userValidator;
    private Printer<User> userPrinter;


    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
            injectDependencies(instance);
        }
        return instance;
    }

    private UserServiceImpl() {}

    private void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private void setBookService(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    private void setUserValidator(Validator<User> userValidator) {
        this.userValidator = userValidator;
    }

    private void setUserPrinter(Printer<User> userPrinter) {
        this.userPrinter = userPrinter;
    }

    private static void injectDependencies(UserServiceImpl userService) {
        userService.setUserValidator(UserValidator.getInstance());
        userService.setUserDao(UserDaoImpl.getInstance());
        userService.setBookService(BookServiceImpl.getInstance());
        userService.setUserPrinter(UserPrinter.getInstance());
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
    public void rentBook(User user, Book book) {
        if (!exist(user)) {
            throw new InvalidEntityException("User not exist");
        }
        if (!bookService.exist(book)){
            throw new InvalidEntityException("Book not exist");
        }
        if (book.getCurrentUser() != null) {
            throw new InvalidEntityException("Book already rented");
        }
        try {
            bookService.rentBook(book.getId(), user);
        } catch (FileAccessException e) {
            throw new RuntimeException(e);
        }
        userDao.rentBook(user.getId(), book);
    }

    @Override
    public void returnBook(User user, Book book){
        if (!exist(user)) {
            throw new InvalidEntityException("User not exist");
        }
        if (!bookService.exist(book)) {
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