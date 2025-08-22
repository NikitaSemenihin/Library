package com.semenihin.checkConnection;

import com.semenihin.connector.LBDatabaseConnector;

import java.sql.Connection;

public class LBCheckConnection {
    public static void main(String[] args) {
        try (Connection conn = LBDatabaseConnector.getConnection()) {
            System.out.println("✅ Подключение к MySQL успешно!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
