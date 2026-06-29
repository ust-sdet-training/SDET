package com.ust.sdet.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.ust.sdet.specification.CommonSpec.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateBookingTest {
    private static String TOKEN;
    @BeforeAll
    static void setup() {
        TOKEN = token();
    }

    @Test
    @DisplayName("TC_1: Authentication test")
    void getValidToken(){
        given()
                .spec(commonReqSpec())
                .body(Map.of(
                        "username", "admin",
                "password", "password123"))
                .when().post("/auth")
                .then()
                .spec(commonResSpec())
                .body("token", notNullValue());
    }

    @Test
    void test() {
        var data = Map.of(
                "firstname", "test",
                "lastname", "data",
                "totalprice", 111,
                "depositpaid", true,
                "bookingdates", Map.of(
                        "checkin", "2018-01-01",
                        "checkout", "2019-01-01"),
                "additionalneeds", "Breakfast");
        given()
                .log().all()
                .spec(authReqSpec(TOKEN))
                .body(data)
                .when().post()
                .then()
                .log().all()
                .spec(commonResSpec());
    }

    @Test
    @DisplayName("TC_2: Not found")
    void notFoundTest() {
        given()
                .spec(commonReqSpec())
                .when()
                .get("/{bookingid}", 1000000)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("TC_3: Creating -> updating -> verify updated data")
    void testCase() {
        var data = Map.of(
                "firstname", "test",
                "lastname", "data",
                "totalprice", 111,
                "depositpaid", true,
                "bookingdates", Map.of(
                    "checkin", "2018-01-01",
                    "checkout", "2019-01-01"),
                "additionalneeds", "Breakfast");
        //CREATE
        Response createResponse = given()
                .spec(authReqSpec(TOKEN))
                .body(data)
                .when().post()
                .then()
                .log().all()
                .spec(commonResSpec())
                .extract().response();

        //UPDATE
        var dataModified = Map.of(
                "firstname", "test",
                "lastname", "modified",
                "totalprice", 100,
                "depositpaid", true,
                "bookingdates", Map.of(
                        "checkin", "2018-01-01",
                        "checkout", "2019-01-01"),
                "additionalneeds", "Dinner");

        System.out.println(createResponse.asPrettyString());

        Response updateResponse = given()
                .spec(authReqSpec(TOKEN))
                .body(dataModified)
                .when().put("/{bookingid}", createResponse.jsonPath().getLong("bookingid"))
                .then()
                .spec(commonResSpec())
                .extract().response();

        //GET
        Response getResponse = given()
                .spec(authReqSpec(TOKEN))
                .when().get("/{bookingid}", createResponse.jsonPath().getLong("bookingid"))
                .then()
                .spec(commonResSpec())
                .extract().response();
        System.out.println(getResponse.asPrettyString());
    }


}
