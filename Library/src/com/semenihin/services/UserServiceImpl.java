package com.semenihin.services;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.validator.BookValidator;
import com.semenihin.validator.UserValidator;
import com.semenihin.validator.Validator;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private static UserServiceImpl instance;
    private final BookServiceImpl bookService;
    private final Validator userValidator;
    private final Validator bookVallidator;


    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private UserServiceImpl() {
        this.userValidator = new UserValidator();
        this.userDao = UserDao.getInstance();
        this.bookService = BookServiceImpl.getInstance();
        this.bookVallidator = new BookValidator();
    }

    public void createUser(long id, String fullName, String email, String phoneNumber) {
        if (userValidator.validate(new User(id, fullName, email, phoneNumber))) {
            if (userValidator.exist(new User(id, fullName, email, phoneNumber))) {
                userDao.createUser(id, fullName, email, phoneNumber);
            }
        }
    }

    public User getUser(long userid) {
        for (User user : userDao.getUsers()) {
            if (user.getId() == userid) {
                return user;
            }
        }
        return null;
    }

    public void printUsers() {
        userDao.printUsers();
    }

    public void deleteUser(long userId) {
        if (userValidator.exist(userId)) {
            userDao.delete(userId);
        }
    }

    public void rentBook(User user, Book book) {
        if (userValidator.exist(user) && bookVallidator.exist(book)) {
            bookService.rentBook(book.getId(), user);
            userDao.rentBook(user.getId(), book);
        }
    }

    public void returnBook(User user, Book book) {
        if (userValidator.exist(user) && bookVallidator.exist(book)) {
            bookService.returnBook(book.getId());
            userDao.returnBook(user, book);
        }
    }
}
