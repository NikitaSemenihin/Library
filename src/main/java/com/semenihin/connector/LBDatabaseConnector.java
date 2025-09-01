package com.semenihin.connector;

import com.semenihin.exceptions.LBFileAccessException;
import com.semenihin.exceptions.LBNotExistException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class LBDatabaseConnector {
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String PROPERTIES = "db.properties";

    private static final String url;
    private static final String user;
    private static final String password;

    private LBDatabaseConnector() {
    }

    static {
        try (InputStream input = LBDatabaseConnector.class.getClassLoader().getResourceAsStream(PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty(DB_URL);
            user = properties.getProperty(DB_USER);
            password = properties.getProperty(DB_PASSWORD);
        } catch (IOException e) {
            throw new LBNotExistException(PROPERTIES + " file open exception", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
