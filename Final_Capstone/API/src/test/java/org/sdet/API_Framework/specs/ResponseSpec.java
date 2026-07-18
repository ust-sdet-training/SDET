package org.sdet.API_Framework.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public final class ResponseSpec {

    private ResponseSpec(){}

    public static ResponseSpecification ok() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

    }

    public static ResponseSpecification created() {

        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();

    }

    public static ResponseSpecification badRequest() {

        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();

    }

    public static ResponseSpecification unauthorized() {

        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .build();

    }

    public static ResponseSpecification forbidden() {

        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .build();

    }

    public static ResponseSpecification notFound() {

        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();

    }

    public static ResponseSpecification conflict() {

        return new ResponseSpecBuilder()
                .expectStatusCode(409)
                .build();

    }

    public static ResponseSpecification unprocessableEntity() {

        return new ResponseSpecBuilder()
                .expectStatusCode(422)
                .build();

    }

    public static ResponseSpecification serverError() {

        return new ResponseSpecBuilder()
                .expectStatusCode(500)
                .build();

    }

}