package com.semenihin.mapper;

import com.semenihin.entity.User;
import com.semenihin.exceptions.LBFileAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface LBUserMapper {
    List<User> mapUsers(ResultSet rs);
    User mapUser(ResultSet rs, long userId);
}
