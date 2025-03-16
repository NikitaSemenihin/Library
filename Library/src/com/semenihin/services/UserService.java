package com.semenihin.services;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.validator.UserValidator;

public class UserService{
    private UserDao userDao;
    private static UserService instance = new UserService();
    private UserValidator userValidator = new UserValidator();


    public static UserService getInstance(){
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService(){
        this.userDao = UserDao.getInstance();
    }

    public void createUser(){

    }

    public User getUser(int userid){
        for (User user : userDao.getUsers()){
            if (user.getId() == userid){
                return user;
            }
        }
        return null;
    }

    public void getUsers(){
        userDao.update();
    }

    public void deleteUser(){

    }

    public void rentBook(int userId, int bookId){
        BookService.getInstance().rentBook(bookId, userId);
        getUser(userId).rentBook(BookService.getInstance().getBook(bookId));
    }

    public void returnBook(User user, Book book){

    }
}
