package com.semenihin.dao.impl;


import com.semenihin.connector.LBDatabaseConnector;
import com.semenihin.dao.LBUserMySQLDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.services.BookService;
import com.semenihin.services.impl.LBBookServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LBUserMySQLDaoImpl implements LBUserMySQLDao {
    private static LBUserMySQLDaoImpl instance;
    private final String ID = "id";
    private final String USER_ID = "user_id";
    private final String BOOK_ID = "book_id";
    private final String TITLE = "title";
    private final String AUTHOR = "author";
    private final String PAGES = "pages";
    private final String YEAR = "year";
    private final String FULL_NAME = "fullName";
    private final String EMAIL = "email";
    private final String PHONE_NUMBER = "phoneNumber";

    public static LBUserMySQLDaoImpl getInstance() {
        if (instance == null) {
            instance = new LBUserMySQLDaoImpl();
        }
        return instance;
    }

    private LBUserMySQLDaoImpl() {
    }

    @Override
    public List<User> getUsers() {
        Map<Long, User> usersMap = new HashMap<>();
        String userSelect = "SELECT u.id AS user_id, u.fullName, u.email, u.phoneNumber, b.id " +
                "AS book_id, b.title, b.author, b.pages, b.year FROM users u LEFT JOIN books b ON u.id = b.user_id";
        try (Connection connection = LBDatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(userSelect)) {
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

                long bookId = rs.getLong(BOOK_ID);
                if (bookId != 0) {
                    Book book = new Book(
                            bookId,
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
        String userUpdate = "UPDATE users SET fullName=?, email=?, phoneNumber=? WHERE id=?";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(userUpdate)) {

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
        String userSelect = "SELECT b.id, b.title, b.author, b.pages, b.year, " +
                "u.id as user_id, u.fullName, u.email, u.phoneNumber " +
                "FROM books b LEFT JOIN users u ON b.user_id = u.id WHERE u.id=?";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(userSelect)) {

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
                    if (rs.getLong("id") != 0) {
                        user.getRentedBooks().add(new Book(
                                rs.getLong(ID),
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
        String createUser = "INSERT INTO users (id, fullName, email, phoneNumber) VALUES (?, ?, ?, ?)";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(createUser)) {

            stmt.setLong(1, user.getId());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while inserting user", e);
        }
    }

    @Override
    public void deleteUser(long userID) throws LBFileAccessException {
        String deleteBook = "DELETE FROM users WHERE id=?";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(deleteBook)) {

            stmt.setLong(1, userID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while deleting user", e);
        }
    }
}
