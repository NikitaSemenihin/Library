package com.semenihin.services.impl;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
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
        this.userDao = UserDao.getInstance();
        this.bookService = BookServiceImpl.getInstance();
    }

    @Override
    public void createUser(User user) {
        if (userValidator.validate(user)) {
            if (exist(user)) {
                userDao.createUser(user);
            }
        }
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
        if (exist(userId)) {
            userDao.delete(userId);
        }
    }

    @Override
    public void rentBook(User user, Book book) {
        if (exist(user) && bookService.exist(book)) {
            bookService.rentBook(book.getId(), user);
            userDao.rentBook(user.getId(), book);
        }
    }

    @Override
    public void returnBook(User user, Book book) {
        if (exist(user) && bookService.exist(book)) {
            bookService.returnBook(book.getId());
            userDao.returnBook(user, book);
        }
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
