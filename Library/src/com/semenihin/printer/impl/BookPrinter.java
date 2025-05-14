package com.semenihin.printer.impl;

import com.semenihin.entity.Book;
import com.semenihin.printer.Printer;

public class BookPrinter implements Printer<Book> {
    private static BookPrinter instance;

    public static BookPrinter getInstance(){
        if (instance == null) {
            instance = new BookPrinter();
        }
        return instance;
    }

    private BookPrinter(){}


    @Override
    public void print(Book book) {
        System.out.println("\n\n\n" + book);
    }
}
