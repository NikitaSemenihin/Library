package com.semenihin.mapper.impl;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.mapper.Mapper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookMapper implements Mapper<Book> {

    private final UserDao userDao = UserDao.getInstance();
    private final Pattern bookRegEX =
            Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+\"([^\"]+)\"\\s+(\\d+)\\s+(\\d+)(?:\\s+(\\d+))?");

    @Override
    public void map(String line, List<Book> books) {
        Matcher matcher = bookRegEX.matcher(line);
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
                books.add(book.clone());
            }
        }
    }
}
