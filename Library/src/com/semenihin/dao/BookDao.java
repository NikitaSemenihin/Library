package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.DaoCrashException;
import com.semenihin.filReader.impl.BookFileReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private List<Book> books;
    private static BookDao instance;
    private BookFileReader bookFileReader;

    public static BookDao getInstance() throws DaoCrashException {
        if (instance == null) {
            instance = new BookDao();
        }
        return instance;
    }

    private BookDao() throws DaoCrashException {
        this.bookFileReader = BookFileReader.getInstance();
        try {
            this.books = bookFileReader.readEntitiesFromFile();
        } catch (FileNotFoundException e) {
            throw new DaoCrashException(e);
        }
    }

    public void createBook(int id, String title, String author, int pages, int year, User user) {
        books.add(new Book(id, title, author, pages, year, user));
    }

    public void updateBook(Book book) {
        for (Book selectedBook : books) {
            if (selectedBook.getId() == book.getId()) {
                selectedBook.setPages(book.getPages());
                selectedBook.setYear(book.getYear());
                selectedBook.setAuthor(book.getAuthor());
                selectedBook.setTitle(book.getTitle());
                selectedBook.setCurrentUser(book.getCurrentUser());
            }
        }
    }

    public void delete(Book book) {
        books.remove(book);
    }

    public List<Book> getBooks() {
        List<Book> bookCopy = new ArrayList<>(books);
        return bookCopy;
    }

    public Book getBook(long bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book.clone();
            }
        }
        return null;
    }

    public void rentBook(long bookId, User user) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                book.setCurrentUser(user);
                break;
            }
        }
    }

    public void returnBook(long bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                book.setCurrentUser(null);
                break;
            }
        }
    }
}