package com.semenihin.fileWriter.impl;

import com.semenihin.converter.Converter;
import com.semenihin.converter.impl.BookConverter;
import com.semenihin.converter.impl.UserConverter;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.fileWriter.FileWriterInterface;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UserFileWriter implements FileWriterInterface<User> {

    private static UserFileWriter instance;
    private final Converter<User> userConverter;

    public static UserFileWriter getInstance() {
        if (instance == null) {
            instance = new UserFileWriter();
        }
        return instance;
    }

    private UserFileWriter(){
        this.userConverter = new UserConverter();
    }

    @Override
    public void update(List<User> users) throws FileNotFoundException {
            try(FileWriter fileWriter = new FileWriter("resources/user.txt", false)){
                for (User user : users) {
                    String string = userConverter.convert(user);
                    fileWriter.write(string);
                    fileWriter.append('\n');

                    fileWriter.flush();
                }
            }
            catch (IOException e) {
                throw new FileNotFoundException("File not found");
            }
    }
}
