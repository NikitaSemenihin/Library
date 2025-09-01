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
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import static com.semenihin.constant.LBConstant.*;

public class LBBookMySQLDaoImpl implements LBBookMySQLDao {
    private static final String SQL_CREATE_BOOK = """
            INSERT INTO books (id, title, author, pages, year, user_id) VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String SQL_UPDATE_BOOK = """
            UPDATE books SET title=?, author=?, pages=?, year=? WHERE id=?
            """;
    private static final String SQL_DELETE_BOOK = """
            DELETE FROM books WHERE id=?
            """;
    private static final String SQL_FIND_BOOKS = """
            SELECT b.id as book_id, b.title, b.author, b.pages, b.year,
            u.id as user_id, u.fullName, u.email, u.phoneNumber
            FROM books b LEFT JOIN users u ON b.user_id = u.id
            """;
    private static final String SQL_FIND_BOOK = """
            SELECT b.id as book_id, b.title, b.author, b.pages, b.year, u.id as user_id, u.fullName,
            u.email, u.phoneNumber FROM books b LEFT JOIN users u ON b.user_id = u.id WHERE b.id=?
            """;
    private static final String SQL_RENT_BOOK = """
            UPDATE books SET user_id=? WHERE id=?
            """;
    private static final String SQL_RETURN_BOOK = """
            UPDATE books SET user_id=NULL WHERE id=?
            """;

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
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_BOOK)) {

            statement.setLong(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getPages());
            statement.setInt(5, book.getYear());
            if (book.getCurrentUser() != null) {
                statement.setLong(6, book.getCurrentUser().getId());
            } else {
                statement.setNull(6, Types.BIGINT);
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while inserting book", e);
        }
    }

    @Override
    public void updateBook(Book book) throws LBFileAccessException {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BOOK)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPages());
            statement.setInt(4, book.getYear());
            statement.setLong(5, book.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while updating book", e);
        }

    }

    @Override
    public void delete(Book book) throws LBFileAccessException {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BOOK)) {

            statement.setLong(1, book.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while deleting book", e);
        }
    }

    @Override
    public List<Book> findBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = LBDatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL_FIND_BOOKS)) {
            while (rs.next()) {
                User user = null;
                if (rs.getString(USER_ID) != null) {
                    user = new User(
                            rs.getLong(USER_ID),
                            rs.getString(FULL_NAME),
                            rs.getString(EMAIL),
                            rs.getString(PHONE_NUMBER)
                    );
                }

                Book book = new Book(
                        rs.getLong(BOOK_ID),
                        rs.getString(TITLE),
                        rs.getString(AUTHOR),
                        rs.getInt(PAGES),
                        rs.getInt(YEAR),
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
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BOOK)) {

            statement.setLong(1, bookId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = null;
                    if (rs.getString(USER_ID) != null) {
                        user = new User(
                                rs.getLong(USER_ID),
                                rs.getString(FULL_NAME),
                                rs.getString(EMAIL),
                                rs.getString(PHONE_NUMBER)
                        );
                    }

                    return new Book(
                            rs.getLong(BOOK_ID),
                            rs.getString(TITLE),
                            rs.getString(AUTHOR),
                            rs.getInt(PAGES),
                            rs.getInt(YEAR),
                            user
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while finding book", e);
        }
        return null;
    }

    @Override
    public void rentBook(long bookId, User user) throws LBFileAccessException {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_RENT_BOOK)) {

            statement.setLong(1, user.getId());
            statement.setLong(2, bookId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while renting book", e);
        }
    }

    @Override
    public void returnBook(long bookId) throws LBFileAccessException {
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_RETURN_BOOK)) {

            statement.setLong(1, bookId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while returning book", e);
        }
    }
}
