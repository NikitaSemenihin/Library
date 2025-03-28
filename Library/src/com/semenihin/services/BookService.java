package com.semenihin.services;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;

import java.util.List;

public interface BookService {
    void createBook(int id, String title, String author, int pages, int year, User user);

    void updateBook(Book book);

    List<Book> getBooks();

    Book getBook(long id);

    void deleteBook(int id);

    void printBooks();

    void rentBook(long bookId, User user);

    void returnBook(long bookId);
}
