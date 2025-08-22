package com.semenihin.fileWriter.impl;

import com.semenihin.converter.Converter;
import com.semenihin.converter.impl.LBUserConverter;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.fileWriter.FileWriterInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LBUserFileWriter implements FileWriterInterface<User> {

    private static LBUserFileWriter instance;
    private final Converter<User> userConverter;

    public static LBUserFileWriter getInstance() {
        if (instance == null) {
            instance = new LBUserFileWriter();
        }
        return instance;
    }

    private LBUserFileWriter(){
        this.userConverter = new LBUserConverter();
    }

    @Override
    public void update(List<User> users) throws LBFileAccessException {
            try(FileWriter fileWriter = new FileWriter("src/main/resources/user.txt", false)){
                for (User user : users) {
                    String string = userConverter.convert(user);
                    fileWriter.write(string);
                    fileWriter.append('\n');
                    fileWriter.flush();
                }
            }
            catch (IOException e) {
                throw new LBFileAccessException("File not found", e);
            }
    }
}
