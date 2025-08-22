package com.semenihin.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Cloneable {
    private String fullName;
    private String email;
    private String phoneNumber;
    private final long id;
    private List<Book> rentedBooks;

    public User(long id, String fullName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.rentedBooks = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public long getId() {
        return id;
    }

    public List<Book> getRentedBooks() {
        return rentedBooks;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRentedBooks(List<Book> rentedBooks) {
        this.rentedBooks = rentedBooks;
    }

    @Override
    public String toString() {
        return String.format("\nUser: %S\nE-mail: %S\nPhone number: %S\nRented books: %S",
                fullName, email, phoneNumber, rentedBooks);
    }

    @Override
    public User clone() {
        User userClone = new User(this.getId(), this.getFullName(), this.getEmail(), this.getPhoneNumber());
        userClone.rentedBooks = new ArrayList<>(this.rentedBooks);
        return userClone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(fullName, user.fullName) && Objects.equals(email, user.email)
                && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, phoneNumber, id);
    }
}
