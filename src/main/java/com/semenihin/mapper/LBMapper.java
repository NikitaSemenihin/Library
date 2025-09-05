package com.semenihin.mapper;

import com.semenihin.entity.User;

import java.sql.ResultSet;
import java.util.List;

public interface LBMapper<T> {
    List<T> mapEntities(ResultSet rs);
    T mapEntity(ResultSet rs, long id);
}
