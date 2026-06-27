package com.gamagate1.test;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.gamagate1.model.bookdata;
import static com.gamagate1.support.ResponceSpec.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APITest {

    static int bookingId;
    static String token;

    @BeforeAll
    public static void setup() {
        baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    @Order(1)
    @DisplayName("Create Booking")
    public void createBooking() {

        Response res =
                given()
                        .contentType("application/json")
                        .body(bookdata.booking1)
                        .when()
                        .post("/booking")
                        .then()
                        .spec(response200)
                        .extract()
                        .response();

        bookingId = res.path("bookingid");
        System.out.println("Booking ID: " + bookingId);
    }

    @Test
    @Order(2)
    @DisplayName("Get Booking")
    public void getBooking() {

        given()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .spec(response200)
                .body("firstname", notNullValue());
    }

    @Test
    @Order(3)
    @DisplayName("Create Token")
    public void createToken() {

        Response res =
                given()
                        .contentType("application/json")
                        .body("{\"username\":\"admin\",\"password\":\"password123\"}")
                        .when()
                        .post("/auth")
                        .then()
                        .spec(response200)
                        .extract()
                        .response();

        token = res.path("token");
        System.out.println("Token: " + token);
    }

    @Test
    @Order(4)
    @DisplayName("Delete Booking")
    public void deleteBooking() {

        given()
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .spec(response201);  // delete returns 201
    }
}