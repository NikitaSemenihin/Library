package com.semenihin.converter.impl;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LBBookConverterTest {
    private static final String EXPECTED_CONVERTED_RESULT_WITHOUT_USER = "9999 \"test\" \"test test\" 123 321";
    private static final String EXPECTED_CONVERTED_RESULT_WITH_USER = "9999 \"test\" \"test test\" 123 321 101";
    private static final Long TEST_BOOK_ID = 9999L;
    private static final String TEST_BOOK_TITLE = "test";
    private static final String TEST_BOOK_AUTHOR = "test test";
    private static final int TEST_BOOK_PAGES = 123;
    private static final int TEST_BOOK_YEAR = 321;
    private static final Long TEST_USER_ID = 101L;
    private static final String TEST_USER_FULL_NAME = "user";
    private static final String TEST_USER_EMAIL = "test@email.com";
    private static final String TEST_USER_PHONE_NUMBER = "+3242543224";

    private LBBookConverter bookConverter;
    private Book book;

    @Before
    public void setUp() {
        bookConverter = new LBBookConverter();
        book = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_BOOK_AUTHOR, TEST_BOOK_PAGES, TEST_BOOK_YEAR, null);
    }

    @Test
    public void convertNullUserTest() {
        String result = bookConverter.convert(book);

        assertEquals(EXPECTED_CONVERTED_RESULT_WITHOUT_USER, result);
    }

    @Test
    public void convertNotNullUserTest() {
        book.setCurrentUser(new User(TEST_USER_ID, TEST_USER_FULL_NAME, TEST_USER_EMAIL, TEST_USER_PHONE_NUMBER));

        String result = bookConverter.convert(book);

        assertEquals(EXPECTED_CONVERTED_RESULT_WITH_USER, result);
    }
}