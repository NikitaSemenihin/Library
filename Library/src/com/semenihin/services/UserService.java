package com.semenihin.services;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;

public interface UserService {
    void createUser(User user);

    User getUser(long userid);

    void printUsers();

    void deleteUser(long userId);

    void rentBook(User user, Book book);

    void returnBook(User user, Book book);

    boolean exist(User user);

    boolean exist(long userId);
}