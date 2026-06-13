package com.ust.sdet.api.apiframework.test;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


    @Testcontainers
    public class Containers {

        @Container
        static MySQLContainer<?> mysql =
                new MySQLContainer<>("mysql:8.4")
                        .withDatabaseName("order_db")
                        .withUsername("root")
                        .withPassword("root@123");

        @Test
        void containerShouldStart() {

            System.out.println(
                    "JDBC URL = "
                            + mysql.getJdbcUrl()
            );

            System.out.println(
                    "Username = "
                            + mysql.getUsername()
            );
        }
    }

