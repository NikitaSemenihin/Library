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
import java.util.ArrayList;
import java.util.List;

public class LBUserMySQLDaoImpl implements LBUserMySQLDao {
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
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, fullName, email, phoneNumber FROM users";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                        rs.getLong("id"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading users", e);
        }
        return users;
    }

    @Override
    public void rentBook(long userID, Book book) {

    }

    @Override
    public void returnBook(long userID, long bookID) {

    }

    @Override
    public void updateUser(User user) throws LBFileAccessException {

    }

    @Override
    public User findUser(long userID) {
        return null;
    }

    @Override
    public void createUser(User user) throws LBFileAccessException {

    }

    @Override
    public void deleteUser(long userID) throws LBFileAccessException {

    }

    @Override
    public void updateBookInUser(long userID, long bookID) throws LBFileAccessException {

    }
}
