package sdet.com.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class TripStackRequestSpecs {

    private TripStackRequestSpecs() {
    }

    public static RequestSpecification loginSpec(String baseUrl) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType("application/x-www-form-urlencoded")
                .build();
    }

    public static RequestSpecification authSpec(String baseUrl, String token) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    public static RequestSpecification jsonSpec(String baseUrl, String token) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
