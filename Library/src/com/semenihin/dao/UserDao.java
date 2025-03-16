package com.semenihin.dao;

import com.semenihin.entity.User;
import com.semenihin.filReader.UserFileReader;

import java.util.List;

public class UserDao{
    private UserFileReader userFileReader = new UserFileReader();
    public List<User> users;
    private static UserDao instance;

    public static UserDao getInstance(){
        if (instance == null){
            instance = new UserDao();
        }
        return instance;
    }

    private UserDao() {

    }

    public List<User> getUsers() {
        return users;
    }

    public void initialize(){
        this.users = userFileReader.readEntitiesFromFile();
    }

    public void create() {

    }

    public void read() {

    }

    public void update() {
        for (User user: users){
            System.out.println("\n\n" + user.toString());
        }
    }

    public void delete() {

    }
}
