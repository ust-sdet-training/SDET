package com.ust.sdet.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;

public final class ResponseSpecs {

    private ResponseSpecs() {
    }

    public static ResponseSpecification ok200Spec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(JSON)
                .build();
    }


    public static ResponseSpecification created201Spec() {

        return new ResponseSpecBuilder()

                .expectStatusCode(201)
                .expectContentType(JSON)
                .build();
    }


    public static ResponseSpecification noContent204Spec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();
    }


    public static ResponseSpecification badRequest400Spec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectContentType(JSON)
                .build();
    }


    public static ResponseSpecification unauthorized401Spec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectContentType(JSON)
                .build();
    }


    public static ResponseSpecification forbidden403Spec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .expectContentType(JSON)
                .build();
    }


    public static ResponseSpecification notFound404Spec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType(JSON)
                .build();
    }


    public static ResponseSpecification conflict409Spec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(409)
                .expectContentType(JSON)
                .build();
    }


    public static ResponseSpecification locked423Spec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(423)
                .expectContentType(JSON)
                .build();
    }


    public static ResponseSpecification xml200Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/xml")
                .build();
    }

}