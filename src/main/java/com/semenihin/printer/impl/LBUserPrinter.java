package com.semenihin.printer.impl;

import com.semenihin.entity.User;
import com.semenihin.printer.Printer;

public class LBUserPrinter implements Printer<User> {
    private static LBUserPrinter instance;

    public static LBUserPrinter getInstance(){
        if (instance == null) {
            instance = new LBUserPrinter();
        }
        return instance;
    }

    private LBUserPrinter() {    }

    @Override
    public void print(User user) {
        System.out.println("\n\n" + user.toString());
    }
}
