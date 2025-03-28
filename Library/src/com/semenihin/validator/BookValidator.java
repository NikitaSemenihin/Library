package com.semenihin.validator;

import com.semenihin.entity.Book;

public class BookValidator implements Validator<Book> {
    @Override
    public boolean validate(Book book) {
        return true;
    }

    @Override
    public boolean exist(Book book) {
        return false;
    }

    @Override
    public boolean exist(long objectId) {
        return false;
    }
}
