package com.semenihin.entity;

import com.semenihin.services.UserService;

public class Book {
    public int id;
    public String title;
    public String author;
    public int pages;
    public int currentUserId = -1;
    public int year;

    public Book(int id, String title, String author, int pages, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String toString(){
        if (currentUserId == -1) {
            return "\nBook Title: " + title + "\nBook author: " + author + "\nPages count: " + pages +
                    "\nYear: " + year + "\nUser: Absent";
        } else {
            return "\nBook Title: " + title + "\nBook author: " + author + "\nPages count: " + pages +
                    "\nYear: " + year + "\nUser: " + UserService.getInstance().getUser(currentUserId).getFullName();
        }
    }
}
