package gama.qa.gate3.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static gama.qa.gate3.SpecBuilder.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static java.util.Map.entry;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {

    @Test
    @DisplayName("1: Evaluating GET requests and response")
    void petStatusAndIDCheck(){

        given()
                .spec(petStatusSuccessReqSpec())
                .relaxedHTTPSValidation()
                .when()
                .get("pending")
                .then()
                .spec(petStatusSuccessRespSpec())
                .statusCode(200)
                .body("name", equalTo("doggie"))
                .body("status", equalTo("available"));

        given()
                .spec(petStatusSuccessReqSpec())
                .relaxedHTTPSValidation()
                .when()
                .get("pending")
                .then()
                .spec(petStatusFailRespSpec())
                .statusCode(400)
                .body("name", equalTo("doggie"))
                .body("status", equalTo("available"));

        given()
                .spec(petIDSuccessReqSpec())
                .pathParam("/{id}",123)
                .when()
                .get()
                .then()
                .spec(petIDSuccessRespSpec())
                .statusCode(200);

        given()
                .spec(petIDSuccessReqSpec())
                .pathParam("/{id}",123)
                .when()
                .get()
                .then()
                .spec(petIDSuccessRespSpec())
                .statusCode(400);

        given()
                .spec(petIDSuccessReqSpec())
                .pathParam("/{id}",123)
                .when()
                .get()
                .then()
                .spec(petIDSuccessRespSpec())
                .statusCode(404);
    }

    Map<String, String> updatePet = Map.ofEntries(
            entry("name","jun"),
            entry("status","available")
    );

    @Test
    @DisplayName("2: Evaluating GET requests and response")
    void newPetInStore(){
        given()
                .body(updatePet)
                .when()
                .post("/{id}", 123)
                .then()
                .body("name", equalTo("jun"));
        assertEquals("name", notNullValue());
    }
    @Test
    @DisplayName("3: Evaluating GET")
    void petInStore(){
        given()
                .body(updatePet)
                .when()
                .post("/{id}", 123)
                .then()
                .body("name", equalTo("jun"));
        assertEquals("id", greaterThan(0));
    }

    @Test
    @DisplayName("4: Evaluating GET requests and response")
    void updatePetInStore(){
        given()
                .body(updatePet)
                .when()
                .post("/{id}", 123)
                .then()
                .body("name", equalTo("jun"));
        assertEquals("name", notNullValue());
    }

    Map<String, ? extends Serializable> order = Map.ofEntries(
            entry("id", 0),
            entry("petId", 0),
            entry("quantity",0),
            entry("shipDate","2026-06-23T12:09:34.604Z"),
            entry("status","placed"),
            entry("complete",true)
    );
    @Test
    @DisplayName("5: Evaluating GET requests and response")
    void extractOrderIdAndVerifyOrder(){
        Integer orderID =given()
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .extract()
                .path("id");

        assertEquals("id", notNullValue());

        Integer fetchOrderId =
                given()
                .accept(ContentType.JSON)
                .when()
                .get("/store/order/{orderID}", orderID)
                .then()
                .extract()
                .path("id");

        assertEquals(orderID, fetchOrderId);
    }




}
