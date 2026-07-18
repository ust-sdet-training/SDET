package com.tripstack.config;

import java.util.UUID;

import io.restassured.builder.RequestSpecBuilder;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.METHOD;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.filter.log.LogDetail.URI;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecificationBuilder {

    private RequestSpecificationBuilder() {
    }

    public static RequestSpecification build() {
        return build(null);
    }

    public static RequestSpecification build(String bearerToken) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(ConfigManager.get("BASE_URL"))
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("X-Correlation-ID", UUID.randomUUID().toString())
                .addFilter(new RequestLoggingFilter(METHOD))
                .addFilter(new RequestLoggingFilter(URI))
                .addFilter(new ResponseLoggingFilter(STATUS))
                .addFilter(new ResponseLoggingFilter(BODY));

            // Relax SSL validation to allow tests against self-signed or internal certificates
            builder.setRelaxedHTTPSValidation();

        if (bearerToken != null && !bearerToken.isBlank()) {
            builder.addHeader("Authorization", "Bearer " + bearerToken.trim());
        }

        String apiKey = System.getenv("API_KEY");
        if (apiKey != null && !apiKey.isBlank()) {
            builder.addHeader("X-API-KEY", apiKey.trim());
        }

        return builder.build();
    }
}
