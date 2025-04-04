package com.semenihin;

import com.semenihin.entity.User;
import com.semenihin.services.BookService;
import com.semenihin.services.impl.BookServiceImpl;
import com.semenihin.services.UserService;
import com.semenihin.services.impl.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserServiceImpl.getInstance();
        BookService bookService = BookServiceImpl.getInstance();
//        bookService.printBooks();
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        userService.printUsers();

//        User newUser = new User(-15, "test testovich", "test@test.test", "+3434532");
//        userService.createUser(newUser);
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        userService.printUsers();
    }
}