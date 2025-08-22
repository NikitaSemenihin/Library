package com.semenihin.converter.impl;

import com.semenihin.converter.Converter;
import com.semenihin.entity.User;

public class LBUserConverter implements Converter<User> {
    @Override
    public String convert(User user) {
        return String.format("%d \"%s\" %s %s",
                user.getId(), user.getFullName(), user.getEmail(), user.getPhoneNumber());
    }
}
