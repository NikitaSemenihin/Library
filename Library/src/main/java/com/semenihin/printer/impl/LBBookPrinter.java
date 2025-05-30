package com.semenihin.printer.impl;

import com.semenihin.entity.Book;
import com.semenihin.printer.Printer;

public class LBBookPrinter implements Printer<Book> {
    private static LBBookPrinter instance;

    public static LBBookPrinter getInstance(){
        if (instance == null) {
            instance = new LBBookPrinter();
        }
        return instance;
    }

    private LBBookPrinter(){}


    @Override
    public void print(Book book) {
        System.out.println("\n\n\n" + book);
    }
}
