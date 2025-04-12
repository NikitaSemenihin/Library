package com.semenihin.services;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;

import java.util.List;

public interface BookService {
    void createBook(Book book);

    void updateBook(Book book);

    List<Book> getBooks();

    Book getBook(long id);

    void deleteBook(int id);

    void printBooks();

    void rentBook(long bookId, User user);

    void returnBook(long bookId);

    boolean exist(long bookId);

    boolean exist(Book book);
}
