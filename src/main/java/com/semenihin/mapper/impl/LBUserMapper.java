package com.semenihin.mapper.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBMapperException;
import com.semenihin.mapper.LBMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.semenihin.constant.LBConstant.AUTHOR;
import static com.semenihin.constant.LBConstant.BOOK_ID;
import static com.semenihin.constant.LBConstant.EMAIL;
import static com.semenihin.constant.LBConstant.FULL_NAME;
import static com.semenihin.constant.LBConstant.PAGES;
import static com.semenihin.constant.LBConstant.PHONE_NUMBER;
import static com.semenihin.constant.LBConstant.TITLE;
import static com.semenihin.constant.LBConstant.USER_ID;
import static com.semenihin.constant.LBConstant.YEAR;


public class LBUserMapper implements LBMapper<User> {

    private LBBookMapper bookMapper;

    public LBUserMapper() {
        this.bookMapper = new LBBookMapper();
    }

    @Override
    public List<User> mapEntities(ResultSet resultSet) {
        try {
            Map<Long, User> usersMap = new HashMap<>();
            while (resultSet.next()) {
                long userId = resultSet.getLong(USER_ID);

                User user = usersMap.get(userId);
                if (user == null) {
                    user = findUserFromResultSetWithoutResultSetIteration(resultSet);
                    usersMap.put(userId, user);
                }

                String bookId = resultSet.getString(BOOK_ID);
                if (bookId != null) {
                    user.getRentedBooks().add(findBookFromResultSet(resultSet, user));
                }
            }
            return new ArrayList<>(usersMap.values());
        } catch (SQLException e) {
            throw new LBMapperException("Error while reading users", e);
        }
    }

    @Override
    public User mapEntity(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                return null;
            }

            User user = findUserFromResultSetWithoutResultSetIteration(resultSet);

            do {
                addBookToUserIfExistWithoutResultSetIteration(resultSet, user);
            } while (resultSet.next());

            return user;
        } catch (SQLException e) {
            throw new LBMapperException("Error while inserting user", e);
        }
    }

    private void addBookToUserIfExistWithoutResultSetIteration(ResultSet resultSet, User user) throws SQLException {
        if (resultSet.getString(BOOK_ID) != null) {
            Book book = bookMapper.mapBookWithoutResultSetIteration(resultSet);
            user.getRentedBooks().add(book);
        }
    }

    private User findUserFromResultSetWithoutResultSetIteration(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong(USER_ID),
                resultSet.getString(FULL_NAME),
                resultSet.getString(EMAIL),
                resultSet.getString(PHONE_NUMBER)
        );
    }

    private Book findBookFromResultSet(ResultSet resultSet, User user) throws SQLException {
        return new Book(
                resultSet.getLong(USER_ID),
                resultSet.getString(TITLE),
                resultSet.getString(AUTHOR),
                resultSet.getInt(PAGES),
                resultSet.getInt(YEAR),
                user);
    }
}
