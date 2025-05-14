package com.semenihin.dao.impl;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.DaoCrashException;
import com.semenihin.exceptions.FileAccessException;
import com.semenihin.filReader.FileReaderInterface;
import com.semenihin.filReader.impl.UserFileReader;
import com.semenihin.fileWriter.impl.UserFileWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final List<User> users;
    private final UserFileWriter userFileWriter;

    private static UserDaoImpl instance;

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    private UserDaoImpl() {
        FileReaderInterface<User> userFileReader = UserFileReader.getInstance();
        this.userFileWriter = UserFileWriter.getInstance();
        try {
            this.users = userFileReader.readEntitiesFromFile();
        } catch (FileNotFoundException e) {
            throw new DaoCrashException(e);
        }
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public void rentBook(long userId, Book book) throws FileAccessException {
        for (User user : users) {
            if (user.getId() == userId) {
                user.rentBook(book);
            }
        }
        try {
            userFileWriter.update(users);
        } catch (FileNotFoundException e) {
            throw new FileAccessException(e);
        }
    }

    public void returnBook(User user, Book book) throws FileAccessException {
        for (User selectedUser : users) {
            if (user.getId() == selectedUser.getId()) {
                selectedUser.getRentedBook().remove(book);
            }
        }
        try {
            userFileWriter.update(users);
        } catch (FileNotFoundException e) {
            throw new FileAccessException(e);
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

    public User findUser(long userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user.clone();
            }
        }
        return null;
    }

    public void createUser(User user) throws FileAccessException {
        this.users.add(user);
        try {
            userFileWriter.update(users);
        } catch (FileNotFoundException e) {
            throw new FileAccessException(e);
        }
    }

    public void deleteUser(long userId) throws FileAccessException {
        users.remove(userId);
        try {
            userFileWriter.update(users);
        } catch (FileNotFoundException e) {
            throw new FileAccessException(e);
        }
    }
}
