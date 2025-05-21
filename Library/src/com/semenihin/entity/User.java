package com.semenihin.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Cloneable {
    private String fullName;
    private String email;
    private String phoneNumber;
    private final long id;
    private List<Book> rentedBook;

    public User(long id, String fullName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.rentedBook = new ArrayList<>();
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

    public List<Book> getRentedBook() {
        return rentedBook;
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

    public void setRentedBook(List<Book> rentedBook) {
        this.rentedBook = rentedBook;
    }

    public void rentBook(Book book) {
        this.rentedBook.add(book);
    }

    public void returnBook(Book book) {
        Book bookToDelete = null;
        for (Book selectedBook : rentedBook) {
            if (selectedBook.getId() == book.getId()) {
                bookToDelete = selectedBook;
            }
        }
        if (bookToDelete != null) {
            rentedBook.remove(bookToDelete);
        }
    }

    @Override
    public String toString() {
        return String.format("\nUser: %S\nE-mai: %S\nPhone number: %S\nRented books: %S",
                fullName, email, phoneNumber, rentedBook);
    }

    @Override
    public User clone() {
        User userClone = new User(this.getId(), this.getFullName(), this.getEmail(), this.getPhoneNumber());
        userClone.rentedBook = new ArrayList<>(this.rentedBook);
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
