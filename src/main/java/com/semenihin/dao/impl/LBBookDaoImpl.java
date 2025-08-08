package com.semenihin.dao.impl;

import com.semenihin.dao.BookDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBDaoCrashException;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.fileReader.impl.LBBookFileReader;
import com.semenihin.fileWriter.FileWriterInterface;
import com.semenihin.fileWriter.impl.LBBookFileWriter;
import com.semenihin.services.UserService;
import com.semenihin.services.impl.LBUserServiceImpl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LBBookDaoImpl implements BookDao {
    private List<Book> books;
    private FileWriterInterface<Book> bookFileWriter;
    private static LBBookDaoImpl instance;
    private UserService userService;
    private LBBookFileReader bookFileReader;

    public static LBBookDaoImpl getInstance() {
        if (instance == null) {
            instance = new LBBookDaoImpl();
        }
        return instance;
    }

    private LBBookDaoImpl() {
        try {
            this.bookFileWriter = LBBookFileWriter.getInstance();
            this.bookFileReader = LBBookFileReader.getInstance();
            this.books = bookFileReader.readEntitiesFromFile();
            this.userService = LBUserServiceImpl.getInstance();
        } catch (LBFileAccessException e) {
            throw new LBDaoCrashException(e);
        }
    }

    @Override
    public void createBook(Book book) throws LBFileAccessException {
        try {
            books.add(book.clone());
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new LBFileAccessException("File can't be open");
        }
    }

    @Override
    public void updateBook(Book book) throws LBFileAccessException {
        try {
            for (Book selectedBook : books) {
                if (selectedBook.getId() == book.getId()) {
                    selectedBook.setPages(book.getPages());
                    selectedBook.setYear(book.getYear());
                    selectedBook.setAuthor(book.getAuthor());
                    selectedBook.setTitle(book.getTitle());
                }
            }
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new LBFileAccessException("File can't be open");
        }
    }

    @Override
    public void delete(Book book) throws LBFileAccessException {
        try {
            books.remove(findBook(book.getId()));
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new LBFileAccessException(e);
        }
    }

    @Override
    public List<Book> getBooks() {
        ArrayList<Book> booksCopy = new ArrayList<>();
        for (Book book : books) {
            booksCopy.add(book.clone());
        }
        return booksCopy;
    }

    @Override
    public Book findBook(long bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book.clone();
            }
        }
        return null;
    }

    @Override
    public void rentBook(long bookId, User user) throws LBFileAccessException {
        try {
            for (Book book : books) {
                if (book.getId() == bookId) {
                    book.setCurrentUser(user);
                }
            }
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new LBFileAccessException(e);
        }
    }

    @Override
    public void returnBook(long bookId) throws LBFileAccessException {
        try {
            for (Book book : books) {
                if (book.getId() == bookId) {
                    book.setCurrentUser(null);
                }
            }
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new LBFileAccessException(e);
        }
    }

    @Override
    public void updateUserInBook(long bookID, long userID) throws LBFileAccessException {
        try {
            for (Book book : books) {
                if (book.getId() == bookID) {
                    book.setCurrentUser(userService.findUser(userID));
                }
            }
            bookFileWriter.update(books);
        } catch (FileNotFoundException e) {
            throw new LBFileAccessException(e);
        }
    }
}