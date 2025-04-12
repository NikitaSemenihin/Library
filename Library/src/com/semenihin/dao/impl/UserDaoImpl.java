package com.semenihin.dao.impl;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.DaoCrashException;
import com.semenihin.filReader.FileReaderInterface;
import com.semenihin.filReader.impl.UserFileReader;

import java.io.FileNotFoundException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private List<User> users;
    private static UserDaoImpl instance;
    private final FileReaderInterface<User> userFileReader;

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    private UserDaoImpl() {
        this.userFileReader = UserFileReader.getInstance();
        try {
            this.users = userFileReader.readEntitiesFromFile();
        } catch (FileNotFoundException e) {
            throw new DaoCrashException(e);
        }
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

    public void createUser(User user) {
        this.users.add(user);
    }

    public void deleteUser(long userId) {
        users.remove(userId);
    }
}
