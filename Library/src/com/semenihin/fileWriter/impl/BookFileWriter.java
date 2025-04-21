package com.semenihin.fileWriter.impl;

import com.semenihin.converter.Converter;
import com.semenihin.converter.impl.BookConverter;
import com.semenihin.entity.Book;
import com.semenihin.fileWriter.FileWriterInterface;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BookFileWriter implements FileWriterInterface<Book> {
    private static BookFileWriter instance;
    private final Converter<Book> bookConverter;

    public static BookFileWriter getInstance() {
        if (instance == null) {
            instance = new BookFileWriter();
        }
        return instance;
    }

    private BookFileWriter(){
        this.bookConverter = new BookConverter();
    }

    @Override
    public void update(List<Book> books) throws FileNotFoundException {
            try(FileWriter fileWriter = new FileWriter("resources/book.txt", false)){
                for (Book book : books) {
                    String string = bookConverter.convert(book);

                    fileWriter.write(string);
                    fileWriter.append('\n');

                    fileWriter.flush();
                }
            }
            catch (IOException e) {
                throw new FileNotFoundException("File not found");
            }
    }
}
