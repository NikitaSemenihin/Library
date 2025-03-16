package com.semenihin.validator;

import com.semenihin.entity.Book;

public class BookValidator implements ValidatorInterface<Book>{
    @Override
    public boolean validate(Book object) {
        return false;
    }
}
