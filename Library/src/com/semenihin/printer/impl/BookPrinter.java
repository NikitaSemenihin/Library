package com.semenihin.printer.impl;

import com.semenihin.entity.Book;
import com.semenihin.printer.Printer;

public class BookPrinter implements Printer<Book> {
    @Override
    public void print(Book book) {
        System.out.println("\n\n\n" + book);
    }
}
