package specs;

import config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class SpecFactory {

    public static RequestSpecification authed(String token) {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.BASE_URL)
                .setContentType("application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}