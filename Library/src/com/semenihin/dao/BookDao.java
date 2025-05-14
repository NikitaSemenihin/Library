package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.FileAccessException;

import java.util.List;

public interface BookDao {

    void createBook(Book book) throws FileAccessException;

    void updateBook(Book book) throws FileAccessException;

    void delete(Book book) throws FileAccessException;

    List<Book> getBooks();

    Book findBook(long bookId);

    void rentBook(long bookId, User user) throws FileAccessException;

    void returnBook(long bookId) throws FileAccessException;
}
