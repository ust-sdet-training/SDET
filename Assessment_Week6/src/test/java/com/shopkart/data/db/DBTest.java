package com.shopkart.data.db;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker = true)
public class DBTest {

    @Container
    public static MySQLContainer mySQL =
            new MySQLContainer("mysql:8.0")
                    .withDatabaseName("retail_test")
                    .withUsername("shopkart_user")
                    .withPassword("YourPassword@123");

    public static String jdbcUrl() {
        return mySQL.getJdbcUrl();
    }

    public static String username() {
        return mySQL.getUsername();
    }

    public static String password() {
        return mySQL.getPassword();
    }
}
