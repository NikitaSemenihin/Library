package com.semenihin.mapper.impl;

import com.semenihin.dao.impl.UserDaoImpl;
import com.semenihin.entity.Book;
import com.semenihin.exceptions.FileAccessException;
import com.semenihin.mapper.Mapper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookMapper implements Mapper<Book> {

    private final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private final Pattern bookRegEX =
            Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+\"([^\"]+)\"\\s+(\\d+)\\s+(\\d+)(?:\\s+(\\d+))?");
    private final int BOOK_ID = 1;
    private final int BOOK_TITLE = 2;
    private final int BOOK_AUTHOR = 3;
    private final int BOOK_PAGES = 4;
    private final int BOOK_YEAR = 5;
    private final int USER_ID = 6;

    @Override
    public void map(String line, List<Book> books) {
        Matcher matcher = bookRegEX.matcher(line);
        if (matcher.matches()) {
            long id = Long.parseLong(matcher.group(BOOK_ID));
            String title = matcher.group(BOOK_TITLE);
            String author = matcher.group(BOOK_AUTHOR);
            int pages = Integer.parseInt(matcher.group(BOOK_PAGES));
            int year = Integer.parseInt(matcher.group(BOOK_YEAR));
            if (matcher.group(USER_ID) != null) {
                long userId = Long.parseLong(matcher.group(USER_ID));
                Book book = new Book(id, title, author, pages, year, null);
                books.add(book);
                userDao.rentBook(Long.parseLong(matcher.group(USER_ID)), book);
                book.setCurrentUser(userDao.findUser(userId) );
            } else {
                Book book = new Book(id, title, author, pages, year, null);
                books.add(book.clone());
            }
        }
    }
}
