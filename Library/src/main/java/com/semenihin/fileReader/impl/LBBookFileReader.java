package com.semenihin.fileReader.impl;

import com.semenihin.entity.Book;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.fileReader.FileReaderInterface;
import com.semenihin.mapper.Mapper;
import com.semenihin.mapper.impl.LBBookMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LBBookFileReader implements FileReaderInterface<Book> {
    private static LBBookFileReader instance;
    private final Mapper<Book> mapper;
    private static final String FILE_PATH = "resources/book.txt";

    public static LBBookFileReader getInstance() {
        if (instance == null) {
            instance = new LBBookFileReader();
        }
        return instance;
    }

    private LBBookFileReader(){
        this.mapper = new LBBookMapper();
    }

    @Override
    public List<Book> readEntitiesFromFile() throws LBFileAccessException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            List<Book> books = new ArrayList<>();
            String line = bufferedReader.readLine();

            while (line != null & !Objects.equals(line, "\n")) {
                mapper.map(line, books);
                line = bufferedReader.readLine();
            }

            return books;
        } catch (IOException e) {
            throw new LBFileAccessException("File cannot be opened", e);
        }
    }
}
