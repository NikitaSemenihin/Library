package com.semenihin.printer.impl;

import com.semenihin.entity.User;
import com.semenihin.printer.Printer;

public class UserPrinter implements Printer<User> {
    @Override
    public void print(User user) {
        System.out.println("\n\n" + user.toString());
    }
}
