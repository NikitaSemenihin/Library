package com.semenihin.validator.impl;

import com.semenihin.dao.LBUserMySQLDao;
import com.semenihin.dao.impl.LBUserMySQLDaoImpl;
import com.semenihin.entity.User;
import com.semenihin.validator.Validator;

public class LBUserValidator implements Validator<User> {
    private static LBUserValidator instance;

    public static LBUserValidator getInstance() {
        if (instance == null){
            instance = new LBUserValidator();
        }
        return instance;
    }

    private LBUserValidator(){
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
