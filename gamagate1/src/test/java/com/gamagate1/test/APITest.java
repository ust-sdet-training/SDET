package com.gamagate1.test;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.gamagate1.support.RequestSpec.auth;
import static io.restassured.RestAssured.given;
import static com.gamagate1.support.ResponceSpec.response200;
import static com.gamagate1.support.ResponceSpec.response404;

import static com.gamagate1.support.ResponceSpec.response201;

import static com.gamagate1.model.orderDatas.orderData;


public class APITest {
    @Test
    @DisplayName("Get pet By Id Using Path Param")
    void testGetPetByIdUsingPathParam() {
        given()
            .spec(auth)
            .pathParam("petId", 658720)
        .when()
            .get("pet/{petId}")
        .then()
            .body("id", equalTo(658720))
            .spec(response200);
    }

    @Test
    @DisplayName("Post a order")
    void postOrderForPet() {
        given()
            .spec(auth)
            .body(orderData)
        .when()
            .post("store/order")
        .then()
            .spec(response201);
    }

    @Test
    @DisplayName("Delete a pet")
    void deletePet() {
        given()
            .spec(auth)
        .when()
            .delete("pet/{petId}",111)
        .then()
            .spec(response404);
    }

}