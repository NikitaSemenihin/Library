package com.semenihin.dao.impl;

import com.semenihin.dao.UserDao;
import com.semenihin.entity.Book;
import com.semenihin.entity.User;
import com.semenihin.exceptions.LBDaoCrashException;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.exceptions.LBNotExistException;
import com.semenihin.fileReader.FileReaderInterface;
import com.semenihin.fileReader.impl.LBUserFileReader;
import com.semenihin.fileWriter.impl.LBUserFileWriter;
import com.semenihin.services.BookService;
import com.semenihin.services.impl.LBBookServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class LBUserDaoImpl implements UserDao {
    private List<User> users;
    private LBUserFileWriter userFileWriter;
    private LBUserFileReader userFileReader;
    private BookService bookService;
    private static LBUserDaoImpl instance;

    public static LBUserDaoImpl getInstance() {
        if (instance == null) {
            instance = new LBUserDaoImpl();
        }
        return instance;
    }

    private LBUserDaoImpl() {
        try {
            this.bookService = LBBookServiceImpl.getInstance();
            this.userFileReader = LBUserFileReader.getInstance();
            this.userFileWriter = LBUserFileWriter.getInstance();
            this.users = userFileReader.readEntitiesFromFile();
        } catch (LBFileAccessException e) {
            throw new LBDaoCrashException(e);
        }
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public void rentBook(long userId, Book book){
        for (User user : users) {
            if (user.getId() == userId) {
                user.getRentedBooks().add(book);
            }
        }
    }

    @Override
    public void returnBook(long userID, long bookID) {
        User userToReturn = null;
        for (User user : users) {
            if (user.getId() == userID) {
                userToReturn = user;
            }
        }

        if (userToReturn != null) {
            for (User selectedUser : users) {
                if (userID == selectedUser.getId()) {
                    ArrayList<Book> booksToDelete = new ArrayList<>();

                    for (Book selectedBook : userToReturn.getRentedBooks()) {
                        if (selectedBook.getId() == bookID) {
                            booksToDelete.add(selectedBook);
                        }
                    }

                    if (!booksToDelete.isEmpty()) {
                        for (Book selectedBook : booksToDelete) {
                            userToReturn.getRentedBooks().remove(selectedBook);
                        }
                    }
                }
            }
        } else throw new LBNotExistException("User not exists");
    }

    @Override
    public void updateUser(User user) throws LBFileAccessException {
        try {
            for (User selectedUser : users) {
                if (selectedUser.getId() == user.getId()) {
                    selectedUser.setEmail(user.getEmail());
                    selectedUser.setFullName(user.getFullName());
                    selectedUser.setPhoneNumber(user.getPhoneNumber());
                    selectedUser.setRentedBooks(user.getRentedBooks());
                }
            }
            userFileWriter.update(users);

            for (Book book : user.getRentedBooks()) {
                bookService.updateUserInBook(book.getId(), user.getId());
            }
        } catch (LBFileAccessException e) {
            throw new LBFileAccessException(e);
        }
    }

    @Override
    public User findUser(long userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user.clone();
            }
        }
        return null;
    }

    @Override
    public void createUser(User user) throws LBFileAccessException {
        try {
            this.users.add(user);
            userFileWriter.update(users);
        } catch (LBFileAccessException e) {
            throw new LBFileAccessException(e);
        }
    }

    @Override
    public void deleteUser(long userId) throws LBFileAccessException {
        try {
            users.remove(findUser(userId));
            userFileWriter.update(users);
        } catch (LBFileAccessException e) {
            throw new LBFileAccessException(e);
        }
    }

    @Override
    public void updateBookInUser(long userID, long bookID) throws LBFileAccessException {
        try {
            for (Book book : findUser(userID).getRentedBooks()){
                if (book.getId() == bookID) {
                    book.setTitle(bookService.findBook(bookID).getTitle());
                    book.setAuthor(bookService.findBook(bookID).getAuthor());
                    book.setYear(bookService.findBook(bookID).getYear());
                    book.setPages(bookService.findBook(bookID).getPages());
                }
            }
            userFileWriter.update(users);
        } catch (LBFileAccessException e) {
            throw new LBFileAccessException(e);
        }
    }
}