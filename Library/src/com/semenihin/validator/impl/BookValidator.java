package com.semenihin.validator.impl;

import com.semenihin.dao.BookDao;
import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.services.UserService;
import com.semenihin.services.impl.UserServiceImpl;
import com.semenihin.validator.Validator;

public class BookValidator implements Validator<Book> {
    private static BookValidator instance;
    private final BookDao bookDao;

    public static BookValidator getInstance() {
        if (instance == null) {
            return new BookValidator();
        }
        return instance;
    }

    private BookValidator(){
        bookDao = BookDao.getInstance();
    }

    @Override
    public boolean validate(Book book) {
        return validateBookId(book) && validateBookTitle(book) && validateBookPages(book) &&
                validateBookYear(book) && validateBookAuthor(book) && validateBookUser(book);
    }

    private boolean validateBookId(Book book) {
        return book.getId() >= 0;
    }

    private boolean validateBookTitle(Book book) {
        return book.getTitle() != null;
    }

    private boolean validateBookAuthor(Book book) {
        return book.getAuthor() != null;
    }

    private boolean validateBookYear(Book book) {
        return book.getYear() >= 0;
    }

    private boolean validateBookPages(Book book) {
        return book.getPages() > 0;
    }

    private boolean validateBookUser(Book book) {
        return (book.getCurrentUser() == null) |
                (book.getCurrentUser().getClass() == User.class);
    }
}
