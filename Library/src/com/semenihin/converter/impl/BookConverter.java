package com.semenihin.converter.impl;

import com.semenihin.converter.Converter;
import com.semenihin.entity.Book;

public class BookConverter implements Converter<Book> {
    @Override
    public String convert(Book book) {
        if (book.getCurrentUser() != null){
            return book.getId() + " \"" + book.getTitle() + "\" \"" + book.getAuthor() + "\" " + book.getPages() + " " +
                    book.getYear() + " " + book.getCurrentUser().getId();
        }
        return book.getId() + " \"" + book.getTitle() + "\" \"" + book.getAuthor() + "\" " + book.getPages() + " " +
                book.getYear();
    }
}
