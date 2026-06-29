package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class SpecFactory {

    public static RequestSpecification requestSpec() {

        return new RequestSpecBuilder()
                .setContentType("application/json")
                .build();
    }

}