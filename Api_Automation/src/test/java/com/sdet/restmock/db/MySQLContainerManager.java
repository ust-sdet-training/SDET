package com.sdet.restmock.db;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


    @Testcontainers
    public class MySQLContainerManager
    {

        @Container
        public static MySQLContainer<?> mysql =
                new MySQLContainer<>("mysql:8.4")
                        .withDatabaseName("tripstack")
                        .withUsername("Athuldev")
                        .withPassword("rootest");
    }
