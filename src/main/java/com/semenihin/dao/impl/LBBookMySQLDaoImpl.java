package com.semenihin.dao.impl;

import com.semenihin.connector.LBDatabaseConnector;
import com.semenihin.dao.LBBookMySQLDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LBBookMySQLDaoImpl implements LBBookMySQLDao {
    private static LBBookMySQLDaoImpl instance;

    public static LBBookMySQLDaoImpl getInstance() {
        if (instance == null) {
            instance = new LBBookMySQLDaoImpl();
        }
        return instance;
    }

    private LBBookMySQLDaoImpl() {
    }

    @Override
    public void createBook(Book book) throws LBFileAccessException {

    }

    @Override
    public void updateBook(Book book) throws LBFileAccessException {

    }

    @Override
    public void delete(Book book) throws LBFileAccessException {

    }

    @Override
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.id, b.title, b.author, b.pages, b.year, " +
                "u.id as user_id, u.fullName, u.email, u.phoneNumber " +
                "FROM books b LEFT JOIN users u ON b.user_id = u.id";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User user = null;
                if (rs.getLong("user_id") != 0) {
                    user = new User(
                            rs.getLong("user_id"),
                            rs.getString("fullName"),
                            rs.getString("email"),
                            rs.getString("phoneNumber")
                    );
                }

                Book book = new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("pages"),
                        rs.getInt("year"),
                        user
                );
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading books", e);
        }
        return books;
    }

    @Override
    public Book findBook(long bookId) {
        return null;
    }

    @Override
    public void rentBook(long bookId, User user) throws LBFileAccessException {

    }

    @Override
    public void returnBook(long bookId) throws LBFileAccessException {

    }

    @Override
    public void updateUserInBook(long bookID, long userID) throws LBFileAccessException {

    }
}
