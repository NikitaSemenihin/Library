package com.semenihin.validator;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.User;

public class UserValidator implements Validator<User> {
    UserDao userDao = UserDao.getInstance();

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

    @Override
    public boolean exist(User user) {
        for (User userIter : userDao.getUsers()) {
            if (userIter.equals(user)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean exist(long objectId) {
        for (User userIter : userDao.getUsers()) {
            if (userIter.getId() == objectId) {
                return true;
            }
        }
        return false;
    }
}
