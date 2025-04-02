package com.semenihin.mapper.impl;

import com.semenihin.entity.User;
import com.semenihin.mapper.Mapper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMapper implements Mapper<User> {
    private final Pattern userRegEx =
            Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+([\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,})\\s+([+\\d()\\s-]+)");

    @Override
    public void map(String line, List<User> users) {
        Matcher matcher = userRegEx.matcher(line);
        if (matcher.matches()) {
            long id = Long.parseLong(matcher.group(1));
            String fullName = matcher.group(2);
            String email = matcher.group(3);
            String phoneNumber = matcher.group(4);

            users.add(new User(id, fullName, email, phoneNumber));
        } else {
            System.out.println("Error on string: " + line);
        }
    }
}
