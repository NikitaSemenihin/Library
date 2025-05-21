package com.semenihin.services;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.FileAccessException;

public interface UserService {
    void createUser(User user) throws FileAccessException;

    User findUser(long userid);

    void printUsers();

    void deleteUser(long userId) throws FileAccessException;

    void rentBook(User user, Book book);

    void returnBook(User user, Book book);

    boolean exist(User user);

    boolean exist(long userId);
}