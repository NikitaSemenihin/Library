package com.semenihin.entity;

public class Book implements Cloneable {
    private long id;
    private String title;
    private String author;
    private int pages;
    private User currentUser;
    private int year;

    public Book(long id, String title, String author, int pages, int year, User user) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;
        this.currentUser = user;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public int getYear() {
        return year;
    }

    public long getId() {
        return id;
    }

    public User getCurrentUser() {
        if (currentUser != null) {
            return currentUser.clone();
        } else {
            return null;
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void update(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.pages = book.getPages();
        this.year = book.getYear();
        this.currentUser = book.getCurrentUser();
    }

    @Override
    public String toString() {
        if (currentUser == null) {
            return "\nBook Title: " + title + "\nBook author: " + author + "\nPages count: " + pages +
                    "\nYear: " + year + "\nUser: Absent";
        } else {
            return "\nBook Title: " + title + "\nBook author: " + author + "\nPages count: " + pages +
                    "\nYear: " + year + "\nUser: " + currentUser.getFullName();
        }
    }

    @Override
    public Book clone() {
        return new Book(this.getId(), this.getTitle(), this.getAuthor(), this.getPages(), this.getYear(),
                this.currentUser);
    }
}
