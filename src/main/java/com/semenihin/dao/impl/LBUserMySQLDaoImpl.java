package com.semenihin.dao.impl;


import com.semenihin.connector.LBDatabaseConnector;
import com.semenihin.dao.LBUserMySQLDao;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBDaoException;
import com.semenihin.mapper.LBMapper;
import com.semenihin.mapper.impl.LBUserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
            SELECT b.id as book_id, b.title, b.author, b.pages, b.year,
            u.id as user_id, u.fullName, u.email, u.phoneNumber
            FROM books b RIGHT JOIN users u ON b.user_id = u.id WHERE u.id=?
            """;
    private static final String SQL_FIND_USERS = """
            SELECT u.id AS user_id, u.fullName, u.email, u.phoneNumber, b.id
            AS book_id, b.title, b.author, b.pages, b.year
            FROM users u LEFT JOIN books b ON u.id = b.user_id
            """;

    private static LBUserMySQLDaoImpl instance;

    private final LBMapper<User> mapper;

    public static LBUserMySQLDaoImpl getInstance() {
        if (instance == null) {
            instance = new LBUserMySQLDaoImpl();
        }
        return instance;
    }

    private LBUserMySQLDaoImpl() {
        mapper = new LBUserMapper();
    }

    @Override
    public List<User> findUsers() {
        try (Connection connection = LBDatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_USERS)) {
            return mapper.mapEntities(resultSet);
        } catch (SQLException e) {
            throw new LBDaoException("Error while reading users", e);
        }
    }

    @Override
    public void updateUser(User user) throws LBDaoException {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {

            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhoneNumber());
            statement.setLong(4, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new LBDaoException("Error while updating user", e);
        }
    }

    @Override
    public User findUser(long userID) {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER)) {

            statement.setLong(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                return mapper.mapEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new LBDaoException("Error while reading users", e);
        }
    }

    @Override
    public void createUser(User user) throws LBDaoException {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER)) {

            statement.setLong(1, user.getId());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new LBDaoException("Error while inserting user", e);
        }
    }

    @Override
    public void deleteUser(long userID) throws LBDaoException {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {

            statement.setLong(1, userID);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new LBDaoException("Error while deleting user", e);
        }
    }
}
