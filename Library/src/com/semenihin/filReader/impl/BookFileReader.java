package com.semenihin.filReader.impl;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.filReader.FileReaderInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookFileReader implements FileReaderInterface<Book> {
    private static BookFileReader instance;
    List<Book> books;
    private final String filePath = "resources/book.txt";
    private final Pattern pattern =
            Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+\"([^\"]+)\"\\s+(\\d+)\\s+(\\d+)(?:\\s+(\\d+))?");

    public static BookFileReader getInstance() {
        if (instance == null) {
            instance = new BookFileReader();
        }
        return instance;
    }

    private BookFileReader(){
        this.books = new ArrayList<>();
    }

    @Override
    public List<Book> readEntitiesFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            UserDao userDao = UserDao.getInstance();
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    long id = Long.parseLong(matcher.group(1));
                    String title = matcher.group(2);
                    String author = matcher.group(3);
                    int pages = Integer.parseInt(matcher.group(4));
                    int year = Integer.parseInt(matcher.group(5));
                    if (matcher.group(6) != null) {
                        long userId = Long.parseLong(matcher.group(6));
                        Book book = new Book(id, title, author, pages, year, userDao.getUser(userId));
                        books.add(book);
                        userDao.rentBook(Long.parseLong(matcher.group(6)), book);
                    } else {
                        Book book = new Book(id, title, author, pages, year, null);
                        books.add(book);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}
