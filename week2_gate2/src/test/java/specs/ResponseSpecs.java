package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecs {

    private ResponseSpecs() {}

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

    public static ResponseSpecification noContent204() {

        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();
    }

    public static ResponseSpecification badRequest400() {

        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification unauthorized401() {

        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification forbidden403() {

        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification notFound404() {

        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification conflict409() {

        return new ResponseSpecBuilder()
                .expectStatusCode(409)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification locked423() {

        return new ResponseSpecBuilder()
                .expectStatusCode(423)
                .expectContentType(ContentType.JSON)
                .build();
    }
}