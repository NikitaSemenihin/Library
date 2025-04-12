package com.semenihin.mapper.impl;

import com.semenihin.entity.User;
import com.semenihin.mapper.Mapper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMapper implements Mapper<User> {
    private final Pattern userRegEx =
            Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+([\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,})\\s+([+\\d()\\s-]+)");
    private final int USER_ID = 1;
    private final int USER_FULL_NAME = 2;
    private final int USER_EMAIL = 3;
    private final int USER_PHONE_NUMBER = 4;

    @Override
    public void map(String line, List<User> users) {
        Matcher matcher = userRegEx.matcher(line);
        if (matcher.matches()) {
            long id = Long.parseLong(matcher.group(USER_ID));
            String fullName = matcher.group(USER_FULL_NAME);
            String email = matcher.group(USER_EMAIL);
            String phoneNumber = matcher.group(USER_PHONE_NUMBER);

            users.add(new User(id, fullName, email, phoneNumber));
        } else {
            System.out.println("Error on string: " + line);
        }
    }
}
