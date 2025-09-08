package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBDaoException;
import com.semenihin.exceptions.LBFileAccessException;

import java.util.List;

public interface LBBookMySQLDao {
    void createBook(Book book) throws LBDaoException;

    void updateBook(Book book) throws LBDaoException;

    void delete(long id) throws LBDaoException;

    List<Book> findBooks();

    Book findBook(long bookId);

    void rentBook(long bookId, User user) throws LBDaoException;

    void returnBook(long bookId) throws LBDaoException;

}
