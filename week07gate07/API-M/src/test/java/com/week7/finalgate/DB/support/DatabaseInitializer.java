package com.week7.finalgate.DB.support;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    private DatabaseInitializer() {}

    public static void initialize() {

        executeScript("schema.sql");
        executeScript("seed.sql");

    }

    private static void executeScript(String fileName) {

        try (

                Connection connection =
                        JdbcUtil.getConnection();

                Statement statement =
                        connection.createStatement()

        ) {

            InputStream inputStream =
                    DatabaseInitializer.class
                            .getClassLoader()
                            .getResourceAsStream(fileName);

            if (inputStream == null) {

                throw new RuntimeException(
                        fileName + " not found."
                );

            }

            String sql =
                    new BufferedReader(
                            new InputStreamReader(
                                    inputStream,
                                    StandardCharsets.UTF_8
                            )
                    )
                            .lines()
                            .collect(Collectors.joining("\n"));

            for (String query : sql.split(";")) {

                if (!query.trim().isEmpty()) {

                    statement.execute(query);

                }

            }

        }

        catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}