package com.semenihin.mapper.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBDaoException;
import com.semenihin.exceptions.LBMapperException;
import com.semenihin.mapper.LBMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.semenihin.constant.LBConstant.AUTHOR;
import static com.semenihin.constant.LBConstant.BOOK_ID;
import static com.semenihin.constant.LBConstant.EMAIL;
import static com.semenihin.constant.LBConstant.FULL_NAME;
import static com.semenihin.constant.LBConstant.PAGES;
import static com.semenihin.constant.LBConstant.PHONE_NUMBER;
import static com.semenihin.constant.LBConstant.TITLE;
import static com.semenihin.constant.LBConstant.USER_ID;
import static com.semenihin.constant.LBConstant.YEAR;

public class LBBookMapper implements LBMapper<Book> {
    @Override
    public List<Book> mapEntities(ResultSet resultSet) {
        try {
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = mapBookWithoutResultSetIteration(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new LBMapperException(e);
        }
    }

    @Override
    public Book mapEntity(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                return null;
            }
            return mapBookWithoutResultSetIteration(resultSet);
        } catch (SQLException e) {
            throw new LBMapperException(e);
        }
    }

    public Book mapBookWithoutResultSetIteration(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getLong(BOOK_ID),
                resultSet.getString(TITLE),
                resultSet.getString(AUTHOR),
                resultSet.getInt(PAGES),
                resultSet.getInt(YEAR),
                findUserFromResultSet(resultSet)
        );
    }

    private User findUserFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.getString(USER_ID) == null) {
            return null;
        }
        return new User(
                resultSet.getLong(USER_ID),
                resultSet.getString(FULL_NAME),
                resultSet.getString(EMAIL),
                resultSet.getString(PHONE_NUMBER)
        );
    }
}

