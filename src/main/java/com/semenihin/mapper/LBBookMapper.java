package com.semenihin.mapper;

import com.semenihin.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface LBBookMapper {
    List<Book> mapBooks(ResultSet rs) throws SQLException;
    Book mapBook(ResultSet rs) throws SQLException;
}
