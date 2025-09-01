package com.semenihin.mapper.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBInvalidEntityException;
import com.semenihin.mapper.LBUserMapper;

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
    public List<User> mapUsers(PreparedStatement statement) {
        try (ResultSet rs = statement.executeQuery()) {
            Map<Long, User> usersMap = new HashMap<>();
            while (rs.next()) {
                long userId = rs.getLong(USER_ID_IN_BOOK);

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

                String bookId = rs.getString(BOOK_ID_IN_SQL);
                if (bookId != null) {
                    Book book = new Book(
                            rs.getLong(BOOK_ID_IN_SQL),
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
            throw new LBInvalidEntityException("Error while reading users", e);
        }

    }

    @Override
    public User mapUser(PreparedStatement statement) {
        User user = null;
        while (rs.next()) {
            if (user == null) {
                user = new User(
                        rs.getLong(USER_ID),
                        rs.getString(FULL_NAME),
                        rs.getString(EMAIL),
                        rs.getString(PHONE_NUMBER)
                );
            }
            if (rs.getString(BOOK_ID_IN_SQL) != null) {
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
    }
}
