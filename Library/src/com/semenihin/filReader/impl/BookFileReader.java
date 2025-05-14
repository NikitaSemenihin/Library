package com.semenihin.filReader.impl;

import com.semenihin.entity.Book;
import com.semenihin.filReader.FileReaderInterface;
import com.semenihin.mapper.Mapper;
import com.semenihin.mapper.impl.BookMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookFileReader implements FileReaderInterface<Book> {
    private static BookFileReader instance;
    private final List<Book> books;
    private final String filePath = "resources/book.txt";
    private final Mapper mapper;

    public static BookFileReader getInstance() {
        if (instance == null) {
            instance = new BookFileReader();
        }
        return instance;
    }

    private BookFileReader(){
        this.books = new ArrayList<>();
        this.mapper = new BookMapper();
    }

    @Override
    public List<Book> readEntitiesFromFile() throws FileNotFoundException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();
            while (line != null & !Objects.equals(line, "\n")) {
                mapper.map(line, books);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}
