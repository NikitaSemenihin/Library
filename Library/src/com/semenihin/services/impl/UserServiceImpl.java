package com.semenihin.services.impl;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.DaoCrashException;
import com.semenihin.exceptions.InvalidEntityException;
import com.semenihin.exceptions.ServiceCrashExeption;
import com.semenihin.services.UserService;
import com.semenihin.validator.impl.BookValidator;
import com.semenihin.validator.impl.UserValidator;
import com.semenihin.validator.Validator;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private static UserServiceImpl instance;
    private final BookServiceImpl bookService;
    private final Validator<User> userValidator;


    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private UserServiceImpl() {
        this.userValidator = UserValidator.getInstance();
        try {
            this.userDao = UserDao.getInstance();
        } catch (DaoCrashException e) {
            throw new ServiceCrashExeption(e);
        }
        this.bookService = BookServiceImpl.getInstance();
    }

    @Override
    public void createUser(User user) throws InvalidEntityException {
        if (!userValidator.validate(user)) {
            throw new InvalidEntityException("Incorrect fields");
        }
        if (!exist(user)) {
            throw new InvalidEntityException("User already exist");
        }
        userDao.createUser(user);
    }

    @Override
    public User getUser(long userId) {
        return userDao.getUser(userId);
    }

    @Override
    public void printUsers() {
        for (User user : userDao.getUsers()) {
            System.out.println("\n\n" + user.toString());
        }
    }

    @Override
    public void deleteUser(long userId) {
        if (!exist(userId)) {
            throw new InvalidEntityException("User not exist");
        }
        userDao.delete(userId);
    }

    @Override
    public void rentBook(User user, Book book) {
        if (!exist(user)) {
            throw new InvalidEntityException("User not exist");
        }
        if (!bookService.exist(book)){
            throw new InvalidEntityException("Book not exist");
        }
        bookService.rentBook(book.getId(), user);
        userDao.rentBook(user.getId(), book);
    }

    @Override
    public void returnBook(User user, Book book) {
        if (exist(user)) {
            throw new InvalidEntityException("User not exist");
        }
        if (bookService.exist(book)) {
            throw new InvalidEntityException("Book not exist");
        }
        bookService.returnBook(book.getId());
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

    public boolean exist(long objectId) {
        for (User selectedUser : userDao.getUsers()) {
            if (selectedUser.getId() == objectId) {
                return true;
            }
        }
        return false;
    }
}
