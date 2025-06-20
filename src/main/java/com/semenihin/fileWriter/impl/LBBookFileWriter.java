package com.semenihin.fileWriter.impl;

import com.semenihin.converter.Converter;
import com.semenihin.converter.impl.LBBookConverter;
import com.semenihin.entity.Book;
import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.fileWriter.FileWriterInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LBBookFileWriter implements FileWriterInterface<Book> {
    private static LBBookFileWriter instance;
    private final Converter<Book> bookConverter;

    public static LBBookFileWriter getInstance() {
        if (instance == null) {
            instance = new LBBookFileWriter();
        }
        return instance;
    }

    private LBBookFileWriter(){
        this.bookConverter = new LBBookConverter();
    }

    @Override
    public void update(List<Book> books) throws LBFileAccessException {
            try(FileWriter fileWriter = new FileWriter("src/main/resources/book.txt", false)){
                for (Book book : books) {
                    String string = bookConverter.convert(book);
                    fileWriter.write(string);
                    fileWriter.append('\n');
                    fileWriter.flush();
                }
            }
            catch (IOException e) {
                throw new LBFileAccessException("File not found", e);
            }
    }
}
