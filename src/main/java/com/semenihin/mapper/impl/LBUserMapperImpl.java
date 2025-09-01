package com.semenihin.mapper.impl;

import com.semenihin.connector.LBDatabaseConnector;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.exceptions.LBNotExistException;
import com.semenihin.mapper.LBUserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.semenihin.constant.LBConstant.*;
import static com.semenihin.constant.LBConstant.YEAR;


public class LBUserMapperImpl implements LBUserMapper {

    @Override
    public List<User> mapUsers(ResultSet rs){
        try {
            Map<Long, User> usersMap = new HashMap<>();
            while (rs.next()) {
                long userId = rs.getLong(USER_ID);

                User user = usersMap.get(userId);
                if (user == null) {
                    user = new User(
                            userId,
                            rs.getString(FULL_NAME),
                            rs.getString(EMAIL),
                            rs.getString(PHONE_NUMBER)
                    );
                    usersMap.put(userId, user);
                }

                String bookId = rs.getString(BOOK_ID);
                if (bookId != null) {
                    Book book = new Book(
                            rs.getLong(BOOK_ID),
                            rs.getString(TITLE),
                            rs.getString(AUTHOR),
                            rs.getInt(PAGES),
                            rs.getInt(YEAR),
                            user
                    );
                    user.getRentedBooks().add(book);
                }
            }
            return new ArrayList<>(usersMap.values());
        } catch (SQLException e) {
            throw new LBNotExistException("Error while reading users", e);
        }
    }

    @Override
    public User mapUser(ResultSet rs, long userId) {
        User user = null;
        try {
            while (rs.next()) {
                if (user == null) {
                    user = new User(
                            userId,
                            rs.getString(FULL_NAME),
                            rs.getString(EMAIL),
                            rs.getString(PHONE_NUMBER)
                    );
                }
                if (rs.getString(BOOK_ID) != null) {
                    user.getRentedBooks().add(new Book(
                            rs.getLong(USER_ID),
                            rs.getString(TITLE),
                            rs.getString(AUTHOR),
                            rs.getInt(PAGES),
                            rs.getInt(YEAR),
                            user));
                }
            }
            return user;
        } catch (SQLException e) {
            throw new LBNotExistException("Error while inserting user", e);
        }
    }
}
