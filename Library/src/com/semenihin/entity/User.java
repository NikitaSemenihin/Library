package com.semenihin.entity;

import java.util.List;

public class User {
    public String fullName;
    public String email;
    public String phoneNumber;
    public int id;
    public List<Book> rentedBook;

    public User(int id, String fullName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public List<Book> getRentedBook() {
        return rentedBook;
    }

    public void rentBook(Book book){
        rentedBook.add(book);
    }

    public String toString(){
        return "\nUser: " + fullName + "\nE-mai: " + email +"\nPhone number: " + phoneNumber + "\nRented books: "
                + rentedBook;
    }
}
