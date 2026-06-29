package com.ust.sdet.tests;

import com.ust.sdet.models.PostRequest;
import com.ust.sdet.specs.RequestSpecs;
import com.ust.sdet.specs.ResponseSpecs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostRequestTest {

    private static String createdRecordId;

    @Test
    @DisplayName("Post request test")
    void createRecord() {
        PostRequest payload = new PostRequest("Automation Testing", "Rest Assured Assignment", 1);
        given()
                .spec(RequestSpecs.request_spec())
                .body(payload)
                .when()
                .post("/posts")
                .then()
                .spec(ResponseSpecs.okJson_201())
                .extract()
                .response()
                .then()
                .body("title", equalTo("Automation Testing"))
                .body("body", equalTo("Rest Assured Assignment"))
                .body("id", notNullValue());
//                .body("userId", notNullValue());


    }
}
