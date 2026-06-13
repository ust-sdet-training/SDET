package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecs {

    public static ResponseSpecification ok200() {

        return new ResponseSpecBuilder()

                .expectStatusCode(200)

                .expectContentType(ContentType.JSON)

                .build();
    }

    public static ResponseSpecification created201() {

        return new ResponseSpecBuilder()

                .expectStatusCode(201)

                .expectContentType(ContentType.JSON)

                .build();
    }
}
