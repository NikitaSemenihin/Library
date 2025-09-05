package com.semenihin.services.impl;

import com.semenihin.dao.LBUserMySQLDao;

import com.semenihin.dao.impl.LBUserMySQLDaoImpl;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.exceptions.LBInvalidEntityException;
import com.semenihin.exceptions.LBNotExistException;
import com.semenihin.printer.Printer;
import com.semenihin.printer.impl.LBUserPrinter;
import com.semenihin.services.BookService;
import com.semenihin.services.UserService;
import com.semenihin.validator.impl.LBUserValidator;
import com.semenihin.validator.Validator;

public class LBUserServiceImpl implements UserService {
    private LBUserMySQLDao userDao;
    private Validator<User> userValidator;
    private static LBUserServiceImpl instance;


    public static LBUserServiceImpl getInstance() {
        if (instance == null) {
            instance = new LBUserServiceImpl();
            injectDependencies(instance);
        }
        return instance;
    }

    private LBUserServiceImpl() {}

    private void setUserDao(LBUserMySQLDao userDao) {
        this.userDao = userDao;
    }

    private void setUserValidator(Validator<User> userValidator) {
        this.userValidator = userValidator;
    }

    private void setUserPrinter(Printer<User> userPrinter) {
    }

    private static void injectDependencies(LBUserServiceImpl userService) {
        userService.setUserValidator(LBUserValidator.getInstance());
        userService.setUserDao(LBUserMySQLDaoImpl.getInstance());
        userService.setUserPrinter(LBUserPrinter.getInstance());
    }

    @Override
    public void createUser(User user) throws LBInvalidEntityException, LBFileAccessException {
        if (!userValidator.validate(user)) {
            throw new LBInvalidEntityException("Incorrect fields");
        }
        if (userDao.findUser(user.getId()) != null) {
            throw new LBInvalidEntityException("User already exist");
        }
        userDao.createUser(user);
    }

    @Override
    public User findUser(long userId) {
        return userDao.findUser(userId);
    }

    @Override
    public void deleteUser(long userId) throws LBFileAccessException {
        if (userDao.findUser(userId) == null) {
            throw new LBNotExistException("User not exist");
        }
        userDao.deleteUser(userId);
    }

    @Override
    public void updateUser(User user) throws LBFileAccessException {
        if (!userValidator.validate(user)) {
            throw new LBInvalidEntityException("Incorrect fields");
        }
        userDao.updateUser(user);
    }
}