package com.ust.sdet.api.dbframework.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class DatabaseConfigTest {
    @Test
    void usesDefaultPostgresPortWhenCloudUrlOmitsPort() {
        DatabaseConfig config = DatabaseConfig.fromDatabaseUrl(
                "mysql://cloud_user:cloud_password@db.example.com/training?sslmode=require"
        );
        assertEquals(
                "jdbc:mysql://db.example.com:5432/training?sslmode=require",
                config.jdbcUrl()
        );
    }
    @Test
    void preservesExplicitPort() {
        DatabaseConfig config = DatabaseConfig.fromDatabaseUrl(
                "mysql://local_user:local_password@localhost:5544/training"
        );
        assertEquals("jdbc:mysql://localhost:5544/training", config.jdbcUrl());
    }
}