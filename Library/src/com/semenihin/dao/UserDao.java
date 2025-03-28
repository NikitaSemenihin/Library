package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.filReader.UserFileReader;

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
        for (User userIter : users) {
            if (user.getId() == userIter.getId()) {
                userIter.getRentedBook().remove(book);
                break;
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
