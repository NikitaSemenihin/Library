package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    void rentBook(long userId, Book book);

    void returnBook(User user, Book book);

    void updateUser(User user);

    User getUser(long userId);

    void createUser(User user);

    void deleteUser(long userId);
}
