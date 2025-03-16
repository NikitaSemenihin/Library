package com.semenihin.services;

import com.semenihin.dao.BookDao;
import com.semenihin.entity.Book;
import com.semenihin.validator.BookValidator;

public class BookService{
    private BookDao bookDao;
    private BookValidator bookValidator = new BookValidator();
    private static BookService instance;

    public static BookService getInstance(){
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    private BookService(){
        this.bookDao = BookDao.getInstance();
    }

    public void createBook(int id, String title, String author, int pages, int year){
        bookDao.createBook(id, title, author, pages, year);

    }

    public void getBooks(){
        bookDao.updateBooks();
    }

    public Book getBook(int id){
        for (Book book : bookDao.getBooks()){
            if (book.getId() == id){
                return book;
            }
        }
        return null;
    }

    public void deleteBook(int id){
        for (Book book : bookDao.getBooks()){
            if (book.getId() == id){
                bookDao.delete(book);
                break;
            }
        }
    }

    public void rentBook(int bookId, int userId){
        for (Book book : bookDao.getBooks()){
            if (book.getId() == bookId){
                book.setCurrentUserId(UserService.getInstance().getUser(userId).getId());
                break;
            }
        }
    }
}
