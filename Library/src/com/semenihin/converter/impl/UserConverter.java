package com.semenihin.converter.impl;

import com.semenihin.converter.Converter;
import com.semenihin.entity.User;

public class UserConverter implements Converter<User> {
    @Override
    public String convert(User user) {
        return user.getId() + " \"" + user.getFullName() + "\" " + user.getEmail() + " " + user.getPhoneNumber();
    }
}
