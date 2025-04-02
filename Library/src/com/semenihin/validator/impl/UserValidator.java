package com.semenihin.validator.impl;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.User;
import com.semenihin.validator.Validator;

public class UserValidator implements Validator<User> {
    private static UserValidator instance;
    private final UserDao userDao = UserDao.getInstance();

    public static UserValidator getInstance() {
        if (instance == null){
            return new UserValidator();
        }
        return instance;
    }

    private UserValidator(){}

    @Override
    public boolean validate(User user) {
        if (user.getId() >= 0) {
            if (user.getFullName() != null) {
                if (user.getEmail() != null) {
                    if (user.getPhoneNumber() != null) {
                        return true;
                    } else {
                        System.out.println("User phone number doesn't exist");
                        return false;
                    }
                } else {
                    System.out.println("User email doesn't exist");
                    return false;
                }
            } else {
                System.out.println("User full name doesn't exist");
                return false;
            }
        } else {
            System.out.println("User Id < 0");
            return false;
        }
    }
}
