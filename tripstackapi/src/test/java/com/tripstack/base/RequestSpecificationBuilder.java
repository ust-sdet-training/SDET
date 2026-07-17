package com.tripstack.base;

import com.tripstack.config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testcontainers.containers.MySQLContainer;

public final class RequestSpecificationBuilder {

    private RequestSpecificationBuilder() {
    }

    public static RequestSpecification requestSpecification() {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getBaseUrl())
                .setContentType(ContentType.JSON)
//                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification authorizedRequest(String token) {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
//                .addFilter(new AllureRestAssured())
                .build();
    }

    public static final class TestContainerManager {

        private static MySQLContainer<?> mysqlContainer;

        private TestContainerManager() {
        }

        public static synchronized void startContainer() {

            if (mysqlContainer == null) {

                mysqlContainer = new MySQLContainer<>(ConfigReader.getMysqlImage())
                        .withDatabaseName(ConfigReader.getDatabaseName())
                        .withUsername(ConfigReader.getDatabaseUsername())
                        .withPassword(ConfigReader.getDatabasePassword());

                mysqlContainer.start();
            }
        }

        public static synchronized void stopContainer() {

            if (mysqlContainer != null) {
                mysqlContainer.stop();
                mysqlContainer = null;
            }
        }

        public static String getJdbcUrl() {
            return mysqlContainer.getJdbcUrl();
        }

        public static String getUsername() {
            return mysqlContainer.getUsername();
        }

        public static String getPassword() {
            return mysqlContainer.getPassword();
        }

        public static MySQLContainer<?> getContainer() {
            return mysqlContainer;
        }
    }
}