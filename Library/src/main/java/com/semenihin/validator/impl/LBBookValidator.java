package com.semenihin.validator.impl;

import com.semenihin.dao.impl.LBBookDaoImpl;
import com.semenihin.dao.BookDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.validator.Validator;

public class LBBookValidator implements Validator<Book> {
    private static LBBookValidator instance;

    public static LBBookValidator getInstance() {
        if (instance == null) {
            return new LBBookValidator();
        }
        return instance;
    }

    private LBBookValidator(){
        BookDao bookDao = LBBookDaoImpl.getInstance();
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
