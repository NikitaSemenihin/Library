package com.semenihin.mapper.impl;

import com.semenihin.dao.impl.LBUserDaoImpl;
import com.semenihin.entity.Book;
import com.semenihin.mapper.Mapper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LBBookMapper implements Mapper<Book> {

    private final LBUserDaoImpl userDao = LBUserDaoImpl.getInstance();
    private final Pattern bookRegEX =
            Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+\"([^\"]+)\"\\s+(\\d+)\\s+(\\d+)(?:\\s+(\\d+))?");
    private static final int BOOK_ID = 1;
    private static final int BOOK_TITLE = 2;
    private static final int BOOK_AUTHOR = 3;
    private static final int BOOK_PAGES = 4;
    private static final int BOOK_YEAR = 5;
    private static final int USER_ID = 6;

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
                book.setCurrentUser(userDao.findUser(userId));
            } else {
                Book book = new Book(id, title, author, pages, year, null);
                books.add(book.clone());
            }
        }
    }
}
