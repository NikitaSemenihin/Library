package com.semenihin.printer.impl;

import com.semenihin.entity.User;
import com.semenihin.printer.Printer;

public class UserPrinter implements Printer<User> {
    private static UserPrinter instance;

    public static UserPrinter getInstance(){
        if (instance == null) {
            instance = new UserPrinter();
        }
        return instance;
    }

    private UserPrinter() {    }

    @Override
    public void print(User user) {
        System.out.println("\n\n" + user.toString());
    }
}
