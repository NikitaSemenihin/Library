package com.semenihin;

import com.semenihin.dao.BookDao;
import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.services.BookService;
import com.semenihin.services.UserService;

public class Main {
    public static void main(String[] args) {

        UserService userService = UserService.getInstance();
        BookService bookService = BookService.getInstance();
        UserDao.getInstance().initialize();
        BookDao.getInstance().initialize();
        bookService.getBooks();
        System.out.println("\n\n---------------------------------------------------------------\n\n");
        userService.getUsers();
    }
}