package com.semenihin.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    User testUser = new User(404L, "test testovich", "test@test.test", "+3434532");
    Book testBook;

    @BeforeEach
    public void setUp() {
        testBook = new Book(101L, "test", "test test", 1234, 4321, testUser);
    }

    @Test
    public void getTitle() {
        assertEquals("test", testBook.getTitle());
    }

    @Test
    public void getAuthor() {
        assertEquals("test test", testBook.getAuthor());
    }

    @Test
    public void getPages() {
        assertEquals(1234, testBook.getPages());
    }

    @Test
    public void getYear() {
        assertEquals(4321, testBook.getYear());
    }

    @Test
    public void getId() {
        assertEquals(101L, testBook.getId());
    }

    @Test
    public void setTitle() {
        testBook.setTitle("newTest");
        assertEquals("newTest", testBook.getTitle());
    }

    @Test
    public void setAuthor() {
        testBook.setAuthor("newTest newTest");
        assertEquals("newTest newTest", testBook.getAuthor());
    }

    @Test
    public void setPages() {
        testBook.setPages(123);
        assertEquals(123, testBook.getPages());
    }

    @Test
    public void setYear() {
        testBook.setYear(321);
        assertEquals(321, testBook.getYear());
    }

    @Test
    public void getCurrentUser() {
        assertEquals(testUser, testBook.getCurrentUser());
    }

    @Test
    public void setCurrentUser() {
        testBook.setCurrentUser(null);
        assertNull(testBook.getCurrentUser());
    }
}