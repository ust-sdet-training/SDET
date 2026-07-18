package com.travelbooking.tests.database;

import com.travelbooking.database.config.DatabaseConfig;
import com.travelbooking.config.TestContainerConfig;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseConnectionTest extends TestContainerConfig {

    @Test
    void shouldConnectToDatabase() throws Exception {

        Connection connection = DatabaseConfig.getConnection();

        assertNotNull(connection);
        assertTrue(connection.isValid(2));

        connection.close();
    }
}