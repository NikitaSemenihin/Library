package com.semenihin.entity;

import java.util.Objects;

public class Book implements Cloneable {
    private final long id;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setYear(int year) {
        this.year = year;
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

    @Override
    public String toString() {
        if (currentUser == null) {
            return String.format("\nBook Title: %s\nBook author: %s\nPages count: %d\nYear: %d\nUser: Absent",
                    title, author, pages, year);
        } else {
            return String.format("\nBook Title: %s\nBook author: %s\nPages count: %d\nYear: %d\nUser: %s",
                    title, author, pages, year, currentUser.getFullName());
        }
    }

    @Override
    public Book clone() {
        return new Book(this.getId(), this.getTitle(), this.getAuthor(), this.getPages(), this.getYear(),
                this.currentUser);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && pages == book.pages && year == book.year && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(currentUser, book.currentUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, pages, currentUser, year);
    }
}
