package com.semenihin.filReader;

import com.semenihin.dao.BookDao;

import java.io.File;
import java.util.List;

public interface FileReaderInterface<T> {
    List<T> readEntitiesFromFile();
}