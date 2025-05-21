package com.semenihin;

import com.semenihin.entity.Book;
import com.semenihin.exceptions.FileAccessException;
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

        Book newBook = new Book(1, "testik", "testok testovich", 1232, 3210, null);
        try {
            bookService.createBook(newBook);
        } catch (FileAccessException e) {
            throw new RuntimeException(e);
        }

        userService.rentBook(userService.findUser(1), newBook);

        System.out.println("\n\n---------------------------------------------------------------\n\n");
        bookService.printBooks();
        System.out.println("\n\n---------------------------------------------------------------\n\n");
        userService.printUsers();


        try {
            bookService.deleteBook(1);
        } catch (FileAccessException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n\n---------------------------------------------------------------\n\n");
        bookService.printBooks();
        System.out.println("\n\n---------------------------------------------------------------\n\n");
        userService.printUsers();


//        User newUser = new User(4, "test testovich", "test@test.test", "+3434532");
//        userService.createUser(newUser);
//        System.out.println("\n\n---------------------------------------------------------------\n\n");
//        userService.printUsers();
    }
}