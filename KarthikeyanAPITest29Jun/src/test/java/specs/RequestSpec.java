package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {

    public static RequestSpecification requestSpecification() {

        return new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
    }
}