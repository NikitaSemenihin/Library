package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    void rentBook(long userID, Book book);

    void returnBook(long userID, long bookID);

    void updateUser(User user) throws LBFileAccessException;

    User findUser(long userID);

    void createUser(User user) throws LBFileAccessException;

    void deleteUser(long userID) throws LBFileAccessException;

    void updateBookInUser(long userID, long bookID) throws LBFileAccessException;
}
