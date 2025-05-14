package com.semenihin;

import com.semenihin.services.BookService;
import com.semenihin.services.impl.BookServiceImpl;
import com.semenihin.services.UserService;
import com.semenihin.services.impl.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserServiceImpl.getInstance();
        BookService bookService = BookServiceImpl.getInstance();
        bookService.printBooks();
        System.out.println("\n\n---------------------------------------------------------------\n\n");
        userService.printUsers();

//        Book newBook = new Book(7, "test", "test testovich", 123, 321, userService.getUser(4));
//        bookService.createBook(newBook);
//
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        bookService.printBooks();
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        userService.printUsers();


//        User newUser = new User(4, "test testovich", "test@test.test", "+3434532");
//        userService.createUser(newUser);
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        userService.printUsers();
    }
}