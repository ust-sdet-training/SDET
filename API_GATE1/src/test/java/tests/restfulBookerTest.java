package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.SpecFactory;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class restfulBookerTest extends BaseTest {

    @Test
    @DisplayName("Get: Get the date as a response")
    void getBookingIds(){

        given()
                .spec(SpecFactory.reqSpec())
                .contentType(ContentType.JSON)
                .when()
                .get("/booking")
                .then()
                .spec(SpecFactory.successResponse())
                .statusCode(200);
    }

    @Test
    @DisplayName("Post: Get the token using username and password")
    void getToken(){

        Map<String, Object> creds = Map.of(
                "username", "admin",
                "password", "password123"
        );

        String token = given()
                .spec(SpecFactory.reqSpec())
                .body(creds)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

//        System.out.println(token);
        assertThat(token, notNullValue());
        assertThat(token.length(), greaterThan(5));

    }


    @Test
    @DisplayName("Get: Get the booking details by ID")
    void getBookingById(){

       Response response = given()
                .spec(SpecFactory.reqSpec())
                .contentType(ContentType.JSON)
                .when()
                .get("/booking/{id}")
                .then()
                .spec(SpecFactory.successResponse())
                        .extract()
                                .path("response");

        assertEquals(200, response.getStatusCode());

        int bookingId = response.jsonPath().getInt("bookingid");

        assertTrue(bookingId > 0);

        assertEquals("Jim",
                response.jsonPath().getString("booking.firstname"));

        assertEquals("Brown",
                response.jsonPath().getString("booking.lastname"));
    }


    @Test
    @DisplayName("Create a new booking")
    void createBookingTest() {

        String details = """
                {
                    "firstname": "Jim",
                    "lastname": "Brown",
                    "totalprice": 111,
                    "depositpaid": true,
                    "bookingdates": {
                        "checkin": "2018-01-01",
                        "checkout": "2019-01-01"
                    },
                    "additionalneeds": "Breakfast"
                }
                """;

        given()
                .spec(SpecFactory.reqSpec())
                .body(details)

                .when()
                .post("/booking")

                .then()
                .spec(SpecFactory.createdResponseSpec())
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo("Jim"))
                .body("booking.lastname", equalTo("Brown"))
                .body("booking.totalprice", equalTo(111))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2018-01-01"))
                .body("booking.bookingdates.checkout", equalTo("2019-01-01"))
                .body("booking.additionalneeds", equalTo("Breakfast"))

                .log().all();
    }



    @Test
    @DisplayName("PUT: Update an Existing Booking")
    void updateBookingTest() {

        // Replace with a valid booking ID
        int bookingId = 1;

        // Replace with a valid auth token
        String token = "your_generated_token";

        String requestBody = """
                {
                    "firstname": "James",
                    "lastname": "Brown",
                    "totalprice": 111,
                    "depositpaid": true,
                    "bookingdates": {
                        "checkin": "2018-01-01",
                        "checkout": "2019-01-01"
                    },
                    "additionalneeds": "Breakfast"
                }
                """;

        given()
                .spec(SpecFactory.reqSpec())
                .cookie("token",token)
                .body(requestBody)
                .when()
                .put("/booking/" + bookingId)

                .then()
                .spec(SpecFactory.successResponse())
                .body("firstname", equalTo("James"))
                .body("lastname", equalTo("Brown"))
                .body("totalprice", equalTo(111))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2018-01-01"))
                .body("bookingdates.checkout", equalTo("2019-01-01"))
                .body("additionalneeds", equalTo("Breakfast"))
                .log().all();


    }


    @Test
    @DisplayName("PATCH: Partially Update Booking")
    void partialUpdateBookingTest() {

        int bookingId = 1;
        String token = "abc123";

        String requestBody = """
                {
                    "firstname":"James",
                    "lastname":"Brown"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .cookie("token", token)
                .body(requestBody)

                .when()
                .patch("/booking/" + bookingId)

                .then()
                .contentType(ContentType.JSON)

                .body("firstname", equalTo("James"))
                .body("lastname", equalTo("Brown"))

                .log().all();
    }


    @Test
    @DisplayName("Delete Booking")
    void deleteBookingTest() {

        int bookingId = 1;
        String token = "abc123";

        given()
                .contentType(ContentType.JSON)
                .cookie("token", token)

                .when()
                .delete("/booking/" + bookingId)

                .then()
                .statusCode(403)
                .log().all();
    }

}



