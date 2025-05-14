package com.semenihin.dao.impl;

import com.semenihin.dao.BookDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.DaoCrashException;
import com.semenihin.exceptions.FileAccessException;
import com.semenihin.filReader.impl.BookFileReader;
import com.semenihin.fileWriter.FileWriterInterface;
import com.semenihin.fileWriter.impl.BookFileWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private final List<Book> books;
    private final FileWriterInterface<Book> bookFileWriter;
    private static BookDaoImpl instance;

    public static BookDaoImpl getInstance() throws DaoCrashException {
        if (instance == null) {
            instance = new BookDaoImpl();
        }
        return instance;
    }

    private BookDaoImpl() throws DaoCrashException {
        this.bookFileWriter = BookFileWriter.getInstance();
        BookFileReader bookFileReader = BookFileReader.getInstance();
        try {
            this.books = bookFileReader.readEntitiesFromFile();
        } catch (FileNotFoundException e) {
            throw new DaoCrashException(e);
        }
    }

    public void createBook(Book book) throws FileAccessException {
        books.add(book.clone());
        try {
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new FileAccessException("File can't be open");
        }
    }

    public void updateBook(Book book) throws FileAccessException {
        for (Book selectedBook : books) {
            if (selectedBook.getId() == book.getId()) {
                selectedBook.setPages(book.getPages());
                selectedBook.setYear(book.getYear());
                selectedBook.setAuthor(book.getAuthor());
                selectedBook.setTitle(book.getTitle());
            }
        }
        try {
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new FileAccessException("File can't be open");
        }
    }

    public void delete(Book book) throws FileAccessException {
        books.remove(book);
        try {
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new FileAccessException(e);
        }
    }

    public List<Book> getBooks() {
        ArrayList<Book> booksCopy = new ArrayList<>();
        for (Book book : books)
        {
           booksCopy.add(book.clone());
        }
        return booksCopy;
    }

    public Book findBook(long bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book.clone();
            }
        }
        return null;
    }

    public void rentBook(long bookId, User user) throws FileAccessException {
        for (Book book : books) {
            if (book.getId() == bookId) {
                book.setCurrentUser(user);
            }
        }
        try {
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new FileAccessException(e);
        }
    }

    public void returnBook(long bookId) throws FileAccessException {
        for (Book book : books) {
            if (book.getId() == bookId) {
                book.setCurrentUser(null);
            }
        }
        try {
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new FileAccessException(e);
        }
    }
}