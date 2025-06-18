package com.semenihin.fileReader.impl;

import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.fileReader.FileReaderInterface;
import com.semenihin.mapper.Mapper;
import com.semenihin.mapper.impl.LBUserMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LBUserFileReader implements FileReaderInterface<User> {
    private static LBUserFileReader instance;
    private final Mapper<User> mapper;
    private static final String FILE_PATH = "resources/user.txt";


    public static LBUserFileReader getInstance(){
        if (instance == null) {
            instance = new LBUserFileReader();
        }
        return instance;
    }

    private LBUserFileReader(){
        this.mapper = new LBUserMapper();
    }

    @Override
    public List<User> readEntitiesFromFile() throws LBFileAccessException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            List<User> users = new ArrayList<>();
            String line = bufferedReader.readLine();

            while (line != null & !Objects.equals(line, "\n")) {
                mapper.map(line, users);
                line = bufferedReader.readLine();
            }

            return users;
        } catch (IOException e) {
            throw new LBFileAccessException("File cannot be opened", e);
        }

    }
}
