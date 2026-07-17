package com.tripstack.db;

import com.tripstack.database.DBUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseConnectionTest {

    @Test
    void verifyDatabaseConnection() throws Exception {

        Connection connection = DBUtils.getConnection();

        assertNotNull(connection);
        assertTrue(connection.isValid(5));

        System.out.println("Database Connected Successfully");

        connection.close();
    }
}