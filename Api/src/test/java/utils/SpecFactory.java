package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class SpecFactory {

    public static RequestSpecification requestSpec() {

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    public static String postsPath() {
        return "/posts";
    }
}