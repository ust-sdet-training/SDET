package com.travelbooking.database;

import com.travelbooking.config.DatabaseConfig;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseConnectionTest {

    @Test
    void shouldConnectToDatabase() throws Exception {

        Connection connection = DatabaseConfig.getConnection();

        assertNotNull(connection);
        assertTrue(connection.isValid(2));

        connection.close();
    }
}