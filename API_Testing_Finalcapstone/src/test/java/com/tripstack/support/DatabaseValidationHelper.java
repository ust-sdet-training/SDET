package com.tripstack.support;

import com.tripstack.db.DatabaseFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseValidationHelper {

    public int validateSimpleQuery() throws Exception {
        try (Connection connection = DatabaseFactory.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }
}
