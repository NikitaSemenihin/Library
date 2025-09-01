package com.semenihin;

import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.services.BookService;
import com.semenihin.services.impl.LBBookServiceImpl;
import com.semenihin.services.UserService;
import com.semenihin.services.impl.LBUserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = LBUserServiceImpl.getInstance();
        BookService bookService = LBBookServiceImpl.getInstance();
        System.out.println(userService.findUser(3));
        bookService.printBooks();
        System.out.println("\n\n---------------------------------------------------------------\n\n");
        userService.printUsers();


        System.out.println(userService.findUser(1));

//        Book newBook = new Book(10, "testik", "testok testovich", 1232, 3210, null);
//        try {
//            bookService.createBook(newBook);
//        } catch (LBFileAccessException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            bookService.rentBook(newBook.getId(), userService.findUser(1));
//        } catch (LBFileAccessException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        bookService.printBooks();
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        userService.printUsers();
//
//
//        try {
//            bookService.deleteBook(10);
//        } catch (LBFileAccessException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        bookService.printBooks();
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        userService.printUsers();
//
//
//        User newUser = new User(5, "test testovich", "test@test.test", "+3434532");
//        try {
//            userService.createUser(newUser);
//        } catch (LBFileAccessException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        userService.printUsers();
    }
}