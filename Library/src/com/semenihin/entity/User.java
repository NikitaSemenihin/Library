package com.semenihin.entity;

import java.util.ArrayList;
import java.util.List;

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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User user = (User) obj;
        return this.getId() == user.getId() && this.getFullName().equals(user.getFullName()) &&
                this.getEmail().equals(user.getEmail()) && this.getPhoneNumber().equals(user.getPhoneNumber()) &&
                this.getRentedBook().equals(user.getRentedBook());
    }
}
