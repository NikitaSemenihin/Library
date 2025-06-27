package com.semenihin.converter.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LBBookConverterTest {
    public LBBookConverter bookConverter = new LBBookConverter();

    Book book = new Book(9999L, "test", "test test", 123, 321, null);

    @Before
    public void setUp() {
        book.setCurrentUser(null);
    }

    @Test
    public void convertNullUserTest() {
        assertEquals(String.format("%d \"%s\" \"%s\" %d %d",
                        book.getId(), book.getTitle(), book.getAuthor(), book.getPages(), book.getYear()),
                bookConverter.convert(book));
    }

    @Test
    public void convertNotNullUserTest() {
        book.setCurrentUser(new User(101L, "user", "test@email.com", "+3242543224"));

        assertEquals(String.format("%d \"%s\" \"%s\" %d %d %d",
                book.getId(), book.getTitle(), book.getAuthor(), book.getPages(), book.getYear(),
                book.getCurrentUser().getId()), bookConverter.convert(book));
    }
}