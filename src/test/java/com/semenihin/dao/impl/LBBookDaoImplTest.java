package com.semenihin.dao.impl;

import com.semenihin.entity.Book;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.fileWriter.FileWriterInterface;
import com.semenihin.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LBBookDaoImplTest {
    @Mock
    private FileWriterInterface<Book> fileWriter;

    @Mock
    private UserService userService;

//    @Mock
//    private ArrayList<Book> books;

    @Mock
    private Book book;

    @Mock
    private Book clone;

    @InjectMocks
    private LBBookDaoImpl bookDao;
//
//    @BeforeEach
//    public void setUp() {
//        books = new ArrayList<>();
//    }

    @Test
    public void createBook() {
//        Book book = mock(Book.class);
//        Book clone = mock(Book.class);
        when(book.clone()).thenReturn(clone);

        try {
            bookDao.createBook(book);
        } catch (LBFileAccessException e) {
            throw new RuntimeException(e);
        }

//        try {
//            verify(fileWriter).update(books);
//        } catch (FileNotFoundException | LBFileAccessException e) {
//            throw new RuntimeException(e);
//        }
    }
}