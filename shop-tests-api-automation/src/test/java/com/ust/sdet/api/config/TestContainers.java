package com.ust.sdet.api.config;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainers {

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4").withDatabaseName("sdet-retail")
                                                                                        .withUsername("YOUR_USERNAME")
                                                                                        .withPassword("YOUR_PASSWORD");


}