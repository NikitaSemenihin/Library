package com.semenihin.fileWriter;

import com.semenihin.exceptions.LBFileAccessException;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileWriterInterface<T> {
    void update(List<T> objects) throws FileNotFoundException, LBFileAccessException;
}
