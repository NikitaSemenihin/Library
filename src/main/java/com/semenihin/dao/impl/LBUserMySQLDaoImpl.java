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
import java.util.List;

public class LBUserMySQLDaoImpl implements LBUserMySQLDao {
    private static LBUserMySQLDaoImpl instance;
    private final BookService bookService;

    public static LBUserMySQLDaoImpl getInstance() {
        if (instance == null) {
            instance = new LBUserMySQLDaoImpl();
        }
        return instance;
    }

    private LBUserMySQLDaoImpl() {
        this.bookService = LBBookServiceImpl.getInstance();
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String userSelect = "SELECT id, fullName, email, phoneNumber FROM users";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(userSelect);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                        rs.getLong("id"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"));
                for (Book book : bookService.getBooks()) {
                    if (book.getCurrentUser() != null) {
                        if (book.getCurrentUser().getId() == user.getId()) {
                            user.getRentedBooks().add(book);
                        }
                    }
                }
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading users", e);
        }
        return users;
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
        String userSelect = "SELECT fullName, email, phoneNumber FROM users WHERE id=?";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(userSelect)) {

            statement.setLong(1, userID);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            userID,
                            rs.getString("fullName"),
                            rs.getString("email"),
                            rs.getString("phoneNumber")
                    );
                    for (Book book : bookService.getBooks()) {
                        if (book.getCurrentUser() != null) {
                            if (book.getCurrentUser().getId() == user.getId()) {
                                user.getRentedBooks().add(book);
                            }
                        }
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
        String createUser = "INSERT INTO books (id, fullName, email, phoneNumber) VALUES (?, ?, ?)";
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
