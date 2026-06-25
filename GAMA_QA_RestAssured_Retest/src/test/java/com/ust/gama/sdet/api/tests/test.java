package com.ust.gama.sdet.api.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ust.gama.sdet.api.ContractSchema.assertWorks.*;
import static com.ust.gama.sdet.api.SpecBuilder.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class test {

    @Test
    @DisplayName("Restful Broker 1 : GET method for different api endpoints")
    void verifyGET_ForBookingIDs() {
        Response response =
                given()
                        .spec(reqSpecJSON("/booking"))
                        .log().ifValidationFails()
                        .when()
                        .log().ifValidationFails()
                        .get()
                        .then()
                        .spec(bookingID_Status200())
                        .log().ifValidationFails()
                        .body("bookingid.size()", greaterThan(0))
                        .extract().response();

        assertOkJsonContract(response, "schemas/json/allBookingID.schema.json");
    }

    @Test
    @DisplayName("Restful Broker 2 : GET method for particular ID")
    void verifyGET_ForByBookingID() {

        Response response =
                given()
                        .spec(reqSpecJSON("/booking"))
                        .log().ifValidationFails()
                        .when()
                        .log().ifValidationFails()
                        .get("/{id}", 121)
                        .then()
                        .log().ifValidationFails()
                        .spec(bookingID_Status200())
                        .statusCode(200)
                        .body("firstname", notNullValue())
                        .body("totalprice", greaterThan(0))
                        .extract().response();

        assertOkJsonContract(response, "schemas/json/bookingId.schema.json");
    }

    @Test
    @DisplayName("Restful Broker 3 : POST  method for Auth")
    void verifyPOST_ForCreatingToken() {

        Response token_resp =
                given()
                        .baseUri("https://restful-booker.herokuapp.com")
                        .basePath("/auth")
                        .contentType(ContentType.JSON)
                        .body(credentialBody)
                        .log().ifValidationFails()
                    .when()
                        .log().ifValidationFails()
                        .post()
                    .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();
        String basic_token = token_resp.jsonPath().getString("token");
        System.out.println(basic_token);
        assertNotNull(basic_token);
    }


    @Test
    @DisplayName("Restful Broker 4 : Extracting booking id from new from new Booking and verifying details")
    void verifyA_PostBookingIdByusingGet() {

        Response order =
                given()
                        .spec(reqSpecJSON("/booking"))
                        .contentType(ContentType.JSON)
                        .body(bookingDetails)
                        .log().ifValidationFails()
                        .when()
                        .log().ifValidationFails()
                        .post()
                        .then()
                        .log().ifValidationFails()
                        .statusCode(200)
                        .extract().response();
        String orderID = order.jsonPath().getString("bookingid");
        String firstname = order.jsonPath().getString("booking.firstname");
        assertNotNull(orderID);

        Response response =
                given()
                        .spec(reqSpecJSON("/booking"))
                        .log().ifValidationFails()
                        .when()
                        .log().ifValidationFails()
                        .get("/{id}", orderID)
                        .then()
                        .log().ifValidationFails()
                        .spec(bookingID_Status200())
                        .statusCode(200)
                        .body("firstname", notNullValue())
                        .body("totalprice", greaterThan(0))
                        .extract().response();

        assertEquals(response.jsonPath().getString("firstname"), firstname);

        assertOkJsonContract(response, "schemas/json/bookingId.schema.json");
    }

}
