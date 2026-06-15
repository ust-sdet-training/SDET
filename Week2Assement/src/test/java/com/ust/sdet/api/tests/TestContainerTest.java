//package com.ust.sdet.api.tests;
//
//import org.testcontainers.containers.MySQLContainer;
//
//
//import org.junit.jupiter.api.Test;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@Testcontainers
//class TestContainerTest {
//
//    @Container
//    static MySQLContainer<?> mysql =
//            new MySQLContainer<>("mysql:8.0")
//                    .withDatabaseName("sdet_retail")
//                    .withUsername(System.getenv("DB_USER"))
//                    .withPassword(System.getenv("DB_PASSWORD"))
//                    .withInitScript("db/schema.sql");
//
//    @Test
//    void containerShouldStart() {
//        System.out.println("JDBC URL: " + mysql.getJdbcUrl());
//        System.out.println("Username : " + mysql.getUsername());
//        System.out.println("Password : " + mysql.getPassword());
//        assert mysql.isRunning();
//    }
//}
