package com.ust.sdet.dbframework.test_db;

import com.ust.sdet.dbframework.config.DatabaseConfig;
import com.ust.sdet.dbframework.support.DbSupport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class DataTesting {
    private static DbSupport database;

    @BeforeAll
    static void setup() {

        DatabaseConfig config = new DatabaseConfig(
                "jdbc:mysql://localhost:3306/order_db",
                "root",
                "Salmon@123"
        );

        database = new DbSupport(config);
    }


    @Test
    @DisplayName("Local M1: MySQL is Reachable through JDBC")
    void localMySqlIsReachable() throws Exception {
        assertTrue(database.isReachable());
    }

}