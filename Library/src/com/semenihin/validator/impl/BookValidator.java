package com.semenihin.validator.impl;

import com.semenihin.dao.BookDao;
import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.validator.Validator;

public class BookValidator implements Validator<Book> {
    private static BookValidator instance;
    private final BookDao bookDao = BookDao.getInstance();

    public static BookValidator getInstance() {
        if (instance == null) {
            return new BookValidator();
        }
        return instance;
    }

    private BookValidator(){}

    @Override
    public boolean validate(Book book) {
        return true;
    }
}
