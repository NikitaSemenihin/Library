package com.semenihin.mapper.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.mapper.LBBookMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.semenihin.constant.LBConstant.*;

public class LBBookMapperImpl implements LBBookMapper {
    @Override
    public List<Book> mapBooks(ResultSet rs) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            User user = null;
            if (rs.getString(USER_ID_IN_BOOK) != null) {
                user = new User(
                        rs.getLong(USER_ID_IN_BOOK),
                        rs.getString(FULL_NAME),
                        rs.getString(EMAIL),
                        rs.getString(PHONE_NUMBER)
                );
            }

            Book book = new Book(
                    rs.getLong(BOOK_ID),
                    rs.getString(TITLE),
                    rs.getString(AUTHOR),
                    rs.getInt(PAGES),
                    rs.getInt(YEAR),
                    user
            );
            books.add(book);
        }
        return books;
    }

    @Override
    public Book mapBook(ResultSet rs) throws SQLException {
        if (rs.next()) {
            User user = null;
            if (rs.getString(USER_ID_IN_BOOK) != null) {
                user = new User(
                        rs.getLong(USER_ID_IN_BOOK),
                        rs.getString(FULL_NAME),
                        rs.getString(EMAIL),
                        rs.getString(PHONE_NUMBER)
                );
            }

            return new Book(
                    rs.getLong(BOOK_ID),
                    rs.getString(TITLE),
                    rs.getString(AUTHOR),
                    rs.getInt(PAGES),
                    rs.getInt(YEAR),
                    user
            );
        }
        return null;
    }
}

