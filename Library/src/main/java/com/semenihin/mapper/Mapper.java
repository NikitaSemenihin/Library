package com.semenihin.mapper;

import java.util.List;

public interface Mapper<T> {
    void map(String line, List<T> entity);
}
