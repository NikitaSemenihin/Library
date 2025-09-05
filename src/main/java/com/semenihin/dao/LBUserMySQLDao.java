package com.semenihin.dao;

import com.semenihin.entity.User;
import com.semenihin.exceptions.LBDaoException;
import com.semenihin.exceptions.LBFileAccessException;

import java.util.List;

public interface LBUserMySQLDao {

    List<User> findUsers();

    void updateUser(User user) throws LBDaoException;

    User findUser(long userID);

    void createUser(User user) throws LBDaoException;

    void deleteUser(long userID) throws LBDaoException;
}
