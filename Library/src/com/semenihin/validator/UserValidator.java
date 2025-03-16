package com.semenihin.validator;

import com.semenihin.entity.User;

public class UserValidator implements ValidatorInterface<User>{
    @Override
    public boolean validate(User object) {
        return false;
    }
}
