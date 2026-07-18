package com.week7.finalgate.DB.tests;

import com.week7.finalgate.DB.config.TestContainerConfig;
import com.week7.finalgate.DB.support.JdbcUtil;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseInitializationTest
        extends TestContainerConfig {

    @Test
    void verifyTablesCreated()
            throws Exception {

        Connection connection =
                JdbcUtil.getConnection();

        Statement statement =
                connection.createStatement();

        ResultSet result =
                statement.executeQuery(
                        "SHOW TABLES"
                );

        int count = 0;

        while (result.next()) {

            System.out.println(
                    result.getString(1)
            );

            count++;

        }

        assertTrue(count >= 2);

    }

}