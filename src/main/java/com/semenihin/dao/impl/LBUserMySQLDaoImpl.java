package com.semenihin.dao.impl;


import com.semenihin.connector.LBDatabaseConnector;
import com.semenihin.dao.LBUserMySQLDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.semenihin.constant.LBConstant.AUTHOR;
import static com.semenihin.constant.LBConstant.BOOK_ID_IN_SQL;
import static com.semenihin.constant.LBConstant.EMAIL;
import static com.semenihin.constant.LBConstant.FULL_NAME;
import static com.semenihin.constant.LBConstant.PAGES;
import static com.semenihin.constant.LBConstant.PHONE_NUMBER;
import static com.semenihin.constant.LBConstant.TITLE;
import static com.semenihin.constant.LBConstant.USER_ID;
import static com.semenihin.constant.LBConstant.USER_ID_IN_BOOK;
import static com.semenihin.constant.LBConstant.YEAR;

public class LBUserMySQLDaoImpl implements LBUserMySQLDao {
    private static final String SQL_CREATE_USER = """
            INSERT INTO users (id, fullName, email, phoneNumber) VALUES (?, ?, ?, ?)
            """;
    private static final String SQL_UPDATE_USER = """
            UPDATE users SET fullName=?, email=?, phoneNumber=? WHERE id=?
            """;
    private static final String SQL_DELETE_USER = """
            DELETE FROM users WHERE id=?
            """;
    private static final String SQL_FIND_USER = """
            SELECT b.id, b.title, b.author, b.pages, b.year,
            u.id as user_id, u.fullName, u.email, u.phoneNumber
            FROM books b LEFT JOIN users u ON b.user_id = u.id WHERE u.id=?
            """;
    private static final String SQL_FIND_USERS = """
            SELECT u.id AS user_id, u.fullName, u.email, u.phoneNumber, b.id
            AS book_id, b.title, b.author, b.pages, b.year
            FROM users u LEFT JOIN books b ON u.id = b.user_id
            """;

    private static LBUserMySQLDaoImpl instance;

    public static LBUserMySQLDaoImpl getInstance() {
        if (instance == null) {
            instance = new LBUserMySQLDaoImpl();
        }
        return instance;
    }

    private LBUserMySQLDaoImpl() {
    }

    @Override
    public List<User> findUsers() {
        Map<Long, User> usersMap = new HashMap<>();
       try (Connection connection = LBDatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL_FIND_USERS)) {
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
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading users", e);
        }
        return new ArrayList<>(usersMap.values());
    }

    @Override
    public void updateUser(User user) throws LBFileAccessException {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE_USER)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setLong(4, user.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while updating user", e);
        }
    }

    @Override
    public User findUser(long userID) {
        User user = null;
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER)) {

            statement.setLong(1, userID);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (user == null) {
                        user = new User(
                                userID,
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
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading users", e);
        }
        return user;
    }

    @Override
    public void createUser(User user) throws LBFileAccessException {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER)) {

            statement.setLong(1, user.getId());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while inserting user", e);
        }
    }

    @Override
    public void deleteUser(long userID) throws LBFileAccessException {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {

            statement.setLong(1, userID);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while deleting user", e);
        }
    }
}
