package com.semenihin.filReader.impl;

import com.semenihin.entity.User;
import com.semenihin.filReader.FileReaderInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFileReader implements FileReaderInterface<User> {
    private static UserFileReader instance;
    List<User> users;
    private final String filePath = "resources/user.txt";
    private final Pattern userRegEx =
            Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+([\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,})\\s+([+\\d()\\s-]+)");

    public static UserFileReader getInstance(){
        if (instance == null) {
            instance = new UserFileReader();
        }
        return instance;
    }

    private UserFileReader(){
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> readEntitiesFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = userRegEx.matcher(line);
                if (matcher.matches()) {
                    users.add(new User(Integer.parseInt(matcher.group(1)), matcher.group(2),
                            matcher.group(3), matcher.group(4)));
                } else {
                    System.out.println("Error on string: " + line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
