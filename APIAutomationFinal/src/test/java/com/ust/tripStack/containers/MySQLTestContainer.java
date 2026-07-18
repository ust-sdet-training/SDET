package com.ust.tripStack.containers;

import org.flywaydb.core.Flyway;
import org.testcontainers.containers.MySQLContainer;

public final class MySQLTestContainer {

    private static final MySQLContainer<?> CONTAINER;

    static {
        CONTAINER = new MySQLContainer<>("mysql:8.0")
                .withDatabaseName("tripstack")
                .withUsername("tripstack_user")
                .withPassword("tripstack_pass");
        CONTAINER.start();

        Flyway.configure()
                .dataSource(CONTAINER.getJdbcUrl(), CONTAINER.getUsername(), CONTAINER.getPassword())
                .locations("classpath:db/migration")
                .load()
                .migrate();
    }

    private MySQLTestContainer() {
    }

    public static MySQLContainer<?> getInstance() {
        return CONTAINER;
    }

    public static String jdbcUrl() {
        return CONTAINER.getJdbcUrl();
    }

    public static String username() {
        return CONTAINER.getUsername();
    }

    public static String password() {
        return CONTAINER.getPassword();
    }
}