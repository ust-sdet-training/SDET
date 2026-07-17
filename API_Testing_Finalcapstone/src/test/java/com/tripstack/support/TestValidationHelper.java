package com.tripstack.support;

import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestValidationHelper {

    private final DatabaseValidationHelper databaseValidationHelper = new DatabaseValidationHelper();

    public void assertJsonSchema(Response response, String schemaFileName) throws IOException {
        String schemaContent = readSchema(schemaFileName);
        assertThat(response.asString(), matchesJsonSchema(schemaContent));
    }

    public void assertDatabaseIsAvailable() throws Exception {
        int result = databaseValidationHelper.validateSimpleQuery();
        assertThat(result, equalTo(1));
    }

    private String readSchema(String fileName) throws IOException {
        return Files.readString(Path.of("src/test/resources/schemas", fileName));
    }
}
