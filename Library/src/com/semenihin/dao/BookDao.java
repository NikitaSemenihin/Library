package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;

import java.util.List;

public interface BookDao {

    void createBook(Book book);

    void updateBook(Book book);

    void delete(Book book);

    List<Book> getBooks();

    Book getBook(long bookId);

    void rentBook(long bookId, User user);

    void returnBook(long bookId);
}
