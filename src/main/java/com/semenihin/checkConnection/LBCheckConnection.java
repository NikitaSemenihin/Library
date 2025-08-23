package com.semenihin.checkConnection;

import java.sql.Connection;
import com.semenihin.connector.LBDatabaseConnector;

public class LBCheckConnection {
    public static void main(String[] args) {
        try (Connection connection = LBDatabaseConnector.getConnection()) {
            System.out.println("✅ Подключение к MySQL успешно!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
