package specs;

import config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    private RequestSpecs() {
    }

    public static RequestSpecification requestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.BASE_URL)
                .setContentType("application/json")
                .build();
    }

    public static RequestSpecification apiSpec() {

        return requestSpec();
    }
}
