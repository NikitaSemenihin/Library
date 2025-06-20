package com.semenihin.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User testUser;
    private List<Book> testBooks;
//    private Book testBook = new Book();

    @BeforeEach
    public void setUp() {
        testUser = new User(404L, "test testovich", "test@test.test", "+3434532");
    }

    @Test
    public void getFullName() {
        assertEquals("test testocivh", testUser.getFullName());
    }

    @Test
    public void getEmail() {
        assertEquals("test@test.test", testUser.getEmail());
    }

    @Test
    public void getPhoneNumber() {
        assertEquals("+3434532", testUser.getPhoneNumber());
    }

    @Test
    public void getId() {
        assertEquals(404L,testUser.getId());
    }

    @Test
    public void getRentedBooks() {
    }

    @Test
    public void setFullName() {
    }

    @Test
    public void setEmail() {
    }

    @Test
    public void setPhoneNumber() {
    }

    @Test
    public void setRentedBooks() {
    }
}