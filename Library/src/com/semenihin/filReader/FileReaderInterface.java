package com.semenihin.filReader;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileReaderInterface<T> {
    List<T> readEntitiesFromFile() throws FileNotFoundException;
}