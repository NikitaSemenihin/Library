package com.semenihin.dao.impl;

import com.semenihin.connector.LBDatabaseConnector;
import com.semenihin.dao.LBBookMySQLDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;

import java.sql.*;
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
        String createBook = "INSERT INTO books (id, title, author, pages, year, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(createBook)) {

            stmt.setLong(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setInt(4, book.getPages());
            stmt.setInt(5, book.getYear());
            if (book.getCurrentUser() != null) {
                stmt.setLong(6, book.getCurrentUser().getId());
            } else {
                stmt.setNull(6, Types.BIGINT);
            }
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while inserting book", e);
        }
    }

    @Override
    public void updateBook(Book book) throws LBFileAccessException {
        String sql = "UPDATE books SET title=?, author=?, pages=?, year=?, user_id=? WHERE id=?";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPages());
            stmt.setInt(4, book.getYear());
            if (book.getCurrentUser() != null) {
                stmt.setLong(5, book.getCurrentUser().getId());
            } else {
                stmt.setNull(5, Types.BIGINT);
            }
            stmt.setLong(6, book.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while updating book", e);
        }

    }

    @Override
    public void delete(Book book) throws LBFileAccessException {
        String deleteBook = "DELETE FROM books WHERE id=?";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(deleteBook)) {

            stmt.setLong(1, book.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while deleting book", e);
        }
    }

    @Override
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        String selectBooks = "SELECT b.id, b.title, b.author, b.pages, b.year, " +
                "u.id as user_id, u.fullName, u.email, u.phoneNumber " +
                "FROM books b LEFT JOIN users u ON b.user_id = u.id";
        try (Connection connection = LBDatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(selectBooks)) {
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
        String bookSelection = "SELECT b.id, b.title, b.author, b.pages, b.year, u.id as user_id, u.fullName, u.email, u.phoneNumber " +
                "FROM books b LEFT JOIN users u ON b.user_id = u.id WHERE b.id=?";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(bookSelection)) {

            stmt.setLong(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = null;
                    if (rs.getLong("user_id") != 0) {
                        user = new User(
                                rs.getLong("user_id"),
                                rs.getString("full_name"),
                                rs.getString("email"),
                                rs.getString("phone_number")
                        );
                    }

                    return new Book(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("pages"),
                            rs.getInt("year"),
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
        String updateBook = "UPDATE books SET user_id=? WHERE id=?";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateBook)) {

            stmt.setLong(1, user.getId());
            stmt.setLong(2, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while renting book", e);
        }
    }

    @Override
    public void returnBook(long bookId) throws LBFileAccessException {
        String updateSQL = "UPDATE books SET user_id=NULL WHERE id=?";
        try (Connection connection = LBDatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateSQL)) {

            stmt.setLong(1, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new LBFileAccessException("Error while returning book", e);
        }
    }
}
