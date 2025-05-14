package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.FileAccessException;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    void rentBook(long userId, Book book) throws FileAccessException;

    void returnBook(User user, Book book) throws FileAccessException;

    void updateUser(User user);

    User findUser(long userId);

    void createUser(User user) throws FileAccessException;

    void deleteUser(long userId) throws FileAccessException;
}
