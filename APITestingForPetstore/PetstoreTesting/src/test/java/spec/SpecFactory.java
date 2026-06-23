package spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public final class SpecFactory {

    private SpecFactory() {
    }

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io")
                .setBasePath("/v2")
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification success200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification success201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification success204() {
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();
    }
}

