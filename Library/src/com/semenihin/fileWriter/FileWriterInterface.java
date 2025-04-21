package com.semenihin.fileWriter;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileWriterInterface<T> {
    void update(List<T> objects) throws FileNotFoundException;
}
