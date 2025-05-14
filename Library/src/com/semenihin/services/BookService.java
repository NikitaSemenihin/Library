package com.semenihin.services;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.FileAccessException;

import java.util.List;

public interface BookService {
    void createBook(Book book) throws FileAccessException;

    void updateBook(Book book) throws FileAccessException;

    List<Book> getBooks();

    Book findBook(long id);

    void deleteBook(int id) throws FileAccessException;

    void printBooks();

    void rentBook(long bookId, User user) throws FileAccessException;

    void returnBook(long bookId) throws FileAccessException;

    boolean exist(long bookId);

    boolean exist(Book book);
}
