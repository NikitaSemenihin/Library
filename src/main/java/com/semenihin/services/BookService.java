package com.semenihin.services;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBDaoException;
import com.semenihin.exceptions.LBFileAccessException;

import java.util.List;

public interface BookService {
    void createBook(Book book) throws LBDaoException;

    void updateBook(Book book) throws LBDaoException;

    List<Book> findBooks();

    Book findBook(long id);

    void deleteBook(long id) throws LBDaoException;

    void rentBook(long bookId, long userId) throws LBDaoException;

    void returnBook(long bookId) throws LBDaoException;
}
