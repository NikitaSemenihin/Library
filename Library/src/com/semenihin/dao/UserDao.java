package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.filReader.impl.UserFileReader;

import java.util.List;

public class UserDao {
    private List<User> users;
    private static UserDao instance;
    UserFileReader userFileReader;

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    private UserDao() {
        this.userFileReader = UserFileReader.getInstance();
        this.users = userFileReader.readEntitiesFromFile();
    }

    public List<User> getUsers() {
        return users;
    }

    public void rentBook(long userId, Book book) {
        for (User user : users) {
            if (user.getId() == userId) {
                user.rentBook(book);
            }
        }
    }

    public void returnBook(User user, Book book) {
        for (User selectedUser : users) {
            if (user.getId() == selectedUser.getId()) {
                selectedUser.getRentedBook().remove(book);
                break;
            }
        }
    }

    public void updateUser(User user){
        for (User selectedUser : users) {
            if (selectedUser.getId() == user.getId()) {
                selectedUser.setEmail(user.getEmail());
                selectedUser.setFullName(user.getFullName());
                selectedUser.setPhoneNumber(user.getPhoneNumber());
                selectedUser.setRentedBook(user.getRentedBook());
            }
        }
    }

    public User getUser(long userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user.clone();
            }
        }
        return null;
    }

    public void createUser(long id, String fullName, String email, String phoneNumber) {
        this.users.add(new User(id, fullName, email, phoneNumber));
    }

    public void printUsers() {
        for (User user : users) {
            System.out.println("\n\n" + user.toString());
        }
    }

    public void delete(long userId) {
        users.remove(userId);
    }
}
