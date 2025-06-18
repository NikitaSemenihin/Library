package com.semenihin.converter.impl;

import com.semenihin.converter.Converter;
import com.semenihin.entity.Book;

public class LBBookConverter implements Converter<Book> {
    @Override
    public String convert(Book book) {
        if (book.getCurrentUser() != null){
            return String.format("%d \"%s\" \"%s\" %d %d %d",
                    book.getId() , book.getTitle(), book.getAuthor(), book.getPages(), book.getYear(),
                    book.getCurrentUser().getId());
        }
        return String.format("%d \"%s\" \"%s\" %d %d",
                book.getId() , book.getTitle(), book.getAuthor(), book.getPages(), book.getYear());
    }
}
