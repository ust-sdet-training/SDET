package tests;

import dbframework.support.DBConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

class DatabaseConnectionTest {
    @Test
    void connectsToMySqlDatabase() throws Exception {
        try (Connection connection = DBConnection.open();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT 1")) {

            Assertions.assertTrue(result.next());
            Assertions.assertEquals(1, result.getInt(1));
        }
    }
}
