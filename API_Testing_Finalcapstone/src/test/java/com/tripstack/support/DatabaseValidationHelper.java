package com.tripstack.support;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tripstack.database.DatabaseManager;

public class DatabaseValidationHelper {

    public int validateSimpleQuery() throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }
}
