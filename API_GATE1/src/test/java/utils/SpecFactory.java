package utils;

import base.BaseTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public class SpecFactory extends BaseTest {


    public static RequestSpecification reqSpec() {

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }


    public static ResponseSpecification successResponse() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(10000L))
                .build();
    }

    public static ResponseSpecification createdResponseSpec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification deleteResponseSpec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }


}
