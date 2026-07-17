package com.tripstack.support;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.tripstack.database.BookingRepository;
import com.tripstack.database.DatabaseManager;
import com.tripstack.model.BookingResponse;

import io.restassured.response.Response;

public class TestValidationHelper {

    public void assertJsonSchema(Response response, String schemaFileName) throws IOException {
        String schemaContent = readSchema(schemaFileName);
        assertThat(response.asString(), matchesJsonSchema(schemaContent));
    }

    public void assertDatabaseIsAvailable() throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {
            resultSet.next();
            assertThat(resultSet.getInt(1), equalTo(1));
        }
    }

    public void assertBookingExists(String pnr) {
        BookingRepository repository = new BookingRepository();
        BookingResponse booking = repository.findBookingByPnr(pnr);
        assertThat(booking, notNullValue());
    }

    private String readSchema(String fileName) throws IOException {
        return Files.readString(Path.of("src/test/resources/schemas", fileName));
    }
}
