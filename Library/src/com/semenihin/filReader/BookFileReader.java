package com.semenihin.filReader;

import com.semenihin.entity.Book;
import com.semenihin.services.UserService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookFileReader implements FileReaderInterface<Book> {
    List<Book> books = new ArrayList<>();
    private final String filePath = "src/com/semenihin/book.txt";
    private final Pattern pattern =
            Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+\"([^\"]+)\"\\s+(\\d+)\\s+(\\d+)(?:\\s+(\\d+))?");

    @Override
    public List<Book> readEntitiesFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    Book book = new Book(Integer.parseInt(matcher.group(1)) ,matcher.group(2), matcher.group(3),
                            Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)));
                    books.add(book);
                    if (matcher.group(6) != null) {
                        book.setCurrentUserId(Integer.parseInt(matcher.group(6)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}
