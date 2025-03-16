package com.semenihin.filReader;

import java.io.File;
import java.util.List;

public interface FileReaderInterface<T> {
    List<T> readEntitiesFromFile();
}
