package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;

import java.util.List;

public interface BookDao {

    void createBook(Book book) throws LBFileAccessException;

    void updateBook(Book book) throws LBFileAccessException;

    void delete(Book book) throws LBFileAccessException;

    List<Book> getBooks();

    Book findBook(long bookId);

    void rentBook(long bookId, User user) throws LBFileAccessException;

    void returnBook(long bookId) throws LBFileAccessException;

    void updateUserInBook(long bookID, long userID) throws LBFileAccessException;
}
