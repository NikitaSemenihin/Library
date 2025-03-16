package com.semenihin.dao;

import com.semenihin.entity.Book;
import com.semenihin.filReader.BookFileReader;

import java.util.List;

public class BookDao{
    List<Book> books;
    private static BookDao instance;
    private BookFileReader bookFileReader = new BookFileReader();

    public static BookDao getInstance(){
        if (instance == null){
            instance = new BookDao();
        }
        return instance;
    }

    private BookDao() {
    }

    public void initialize(){
        this.books = bookFileReader.readEntitiesFromFile();
    }

    public void createBook(int id, String title, String author, int pages, int year) {
        books.add(new Book(id, title, author, pages, year));
    }

    public void updateBooks() {
        for (Book book : books){
            System.out.println("\n\n" + book.toString());
        }
    }

    public void delete(Book book) {
        books.remove(book);
    }

    public List<Book> getBooks() {
        return books;
    }
}
