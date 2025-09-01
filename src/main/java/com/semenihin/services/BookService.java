package com.semenihin.services;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;

import java.util.List;

public interface BookService {
    void createBook(Book book) throws LBFileAccessException;

    void updateBook(Book book) throws LBFileAccessException;

    List<Book> findBooks();

    Book findBook(long id);

    void deleteBook(long id) throws LBFileAccessException;

    void printBooks();

    void rentBook(long bookId, User user) throws LBFileAccessException;

    void returnBook(long bookId) throws LBFileAccessException;
}
