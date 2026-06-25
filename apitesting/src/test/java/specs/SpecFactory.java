package specs;

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
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("User-Agent", "Mozilla/5.0")
                .build();
    }

    public static ResponseSpecification success200() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}