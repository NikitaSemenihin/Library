package com.semenihin.fileReader.impl;

import com.semenihin.entity.Book;
import com.semenihin.mapper.Mapper;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LBBookFileReaderTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Mapper<Book> mapper;

    String line;

    private static String FILE_PATH;

    @Spy
    List<Book> books = new ArrayList<>();

    @InjectMocks
    private LBBookFileReader bookFileReader;

    @Test
    public void testReadEntitiesFromFile() throws Exception{
        bookFileReader.readEntitiesFromFile();
        verify(mapper).map(line, books);
    }
}