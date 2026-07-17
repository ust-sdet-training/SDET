package tests;

import dbframework.support.DBConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class DatabaseConnectionTest {

    @Test
    void verifyConnection() throws Exception {

        Connection connection = DBConnection.getConnection();

        Assertions.assertNotNull(connection);

        System.out.println("====================================");
        System.out.println("Connected to MySQL Successfully");
        System.out.println("====================================");

        connection.close();
    }
}