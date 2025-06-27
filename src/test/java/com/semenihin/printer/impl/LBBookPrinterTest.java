package com.semenihin.printer.impl;

import com.semenihin.entity.Book;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LBBookPrinterTest {

    Book book = new Book(1, "testik", "testok testovich", 1232, 3210, null);

    private LBBookPrinter bookPrinter = new LBBookPrinter();

    @Test
    public void testPrint() {
    }
}