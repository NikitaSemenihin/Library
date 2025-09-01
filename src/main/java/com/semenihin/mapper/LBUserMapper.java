package com.semenihin.mapper;

import com.semenihin.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface LBUserMapper {
    List<User> mapUsers(PreparedStatement statement);
    User mapUser(PreparedStatement statement);
}
