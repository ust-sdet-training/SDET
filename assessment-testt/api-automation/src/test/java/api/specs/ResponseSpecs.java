package api.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public final class ResponseSpecs {
    private ResponseSpecs() {
        // helper class
    }

    public static ResponseSpecification successful() {
        return new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("state", notNullValue())
            .build();
    }

    public static ResponseSpecification created() {
        return new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();
    }

    public static ResponseSpecification forbidden() {
        return new ResponseSpecBuilder()
            .expectStatusCode(403)
            .build();
    }
}
