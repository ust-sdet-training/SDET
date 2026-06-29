package tests;

import io.restassured.RestAssured;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import models.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;
import utils.Config;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = Config.BASE_URL;
    }

    @Test
    @DisplayName("Verifying POST request")
    void post_request(){
        Request request = new Request("Automation Testing", "Rest Assured Assignment", 1);
        given()
                .spec(RequestSpecs.request_spec())
                .body(request)
                .when()
                .post("/posts")
                .then()
                .spec(ResponseSpecs.Created_201())
                .body("title", equalTo("Automation Testing"))
                .body("body", equalTo("Rest Assured Assignment"))
                .body("userId", equalTo(1))
                .body("id", equalTo(101))
                .body(matchesJsonSchemaInClasspath("Schemas/Response-schema.json"));
    }
}