package com.semenihin.validator.impl;

import com.semenihin.dao.impl.UserDaoImpl;
import com.semenihin.entity.User;
import com.semenihin.validator.Validator;

public class UserValidator implements Validator<User> {
    private static UserValidator instance;

    public static UserValidator getInstance() {
        if (instance == null){
            return new UserValidator();
        }
        return instance;
    }

    private UserValidator(){
        UserDaoImpl userDao = UserDaoImpl.getInstance();
    }

    @Override
    public boolean validate(User user) {
        return validateUserId(user) && validateUserEmail(user) &&
                validateUserFullName(user) && validateUserPhoneNumber(user);
    }

    private boolean validateUserId(User user){
        return user.getId() >= 0;
    }

    private boolean validateUserFullName(User user){
        return user.getFullName() != null;
    }

    private boolean validateUserEmail(User user) {
        return user.getEmail() != null;
    }

    private boolean validateUserPhoneNumber(User user){
        return user.getPhoneNumber() != null;
    }
}
