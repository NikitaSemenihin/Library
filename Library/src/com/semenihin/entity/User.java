package com.semenihin.entity;

import java.util.ArrayList;
import java.util.List;

public class User implements Cloneable {
    public String fullName;
    public String email;
    public String phoneNumber;
    public long id;
    public List<Book> rentedBook;

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

    public void rentBook(Book book) {
        this.rentedBook.add(book);
    }

    @Override
    public String toString() {
        return "\nUser: " + fullName + "\nE-mai: " + email + "\nPhone number: " + phoneNumber + "\nRented books: "
                + rentedBook;
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
