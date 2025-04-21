package com.semenihin.filReader.impl;

import com.semenihin.entity.User;
import com.semenihin.filReader.FileReaderInterface;
import com.semenihin.mapper.Mapper;
import com.semenihin.mapper.impl.UserMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFileReader implements FileReaderInterface<User> {
    private static UserFileReader instance;
    private List<User> users;
    private Mapper<User> mapper;
    private final String filePath = "resources/user.txt";


    public static UserFileReader getInstance(){
        if (instance == null) {
            instance = new UserFileReader();
        }
        return instance;
    }

    private UserFileReader(){
        this.users = new ArrayList<>();
        this.mapper = new UserMapper();
    }

    @Override
    public List<User> readEntitiesFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();
            while (line != null & !Objects.equals(line, "\n")) {
                mapper.map(line, users);
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
