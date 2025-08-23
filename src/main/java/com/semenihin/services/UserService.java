package com.semenihin.services;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;

public interface UserService {
    void createUser(User user) throws LBFileAccessException;

    User findUser(long userid);

    void printUsers();

    void deleteUser(long userId) throws LBFileAccessException;

    void rentBook(long userID, long bookID) throws LBFileAccessException;

    void returnBook(long userID, long bookID) throws LBFileAccessException;

    boolean exist(long userId);
}