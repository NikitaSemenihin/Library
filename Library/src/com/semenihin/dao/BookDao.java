package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.filReader.impl.BookFileReader;

import java.util.ArrayList;
import java.util.List;

public class BookDao {
    List<Book> books;
    private static BookDao instance;
    private BookFileReader bookFileReader;

    public static BookDao getInstance() {
        if (instance == null) {
            instance = new BookDao();
        }
        return instance;
    }

    private BookDao() {
        this.bookFileReader = BookFileReader.getInstance();
        this.books = bookFileReader.readEntitiesFromFile();
    }

    public void createBook(int id, String title, String author, int pages, int year, User user) {
        books.add(new Book(id, title, author, pages, year, user));
    }

    public void printBooks() {
        for (Book book : books) {
            System.out.println("\n\n" + book.toString());
        }
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