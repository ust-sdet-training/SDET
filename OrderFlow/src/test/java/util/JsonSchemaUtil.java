package util;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matcher;

public class JsonSchemaUtil {

    public static Matcher<?> matchesSchema(String schemaFileName) {

        return JsonSchemaValidator
                .matchesJsonSchemaInClasspath(
                        "schemas/" + schemaFileName
                );
    }
}