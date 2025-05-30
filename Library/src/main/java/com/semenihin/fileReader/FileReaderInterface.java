package com.semenihin.fileReader;

import com.semenihin.exceptions.LBFileAccessException;

import java.util.List;

public interface FileReaderInterface<T> {
    List<T> readEntitiesFromFile() throws LBFileAccessException;
}