import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HookTest {

    static String token;
    static int bookingId;

    @BeforeAll
    static void setup() {

        Map<String, String> auth = new HashMap<>();
        auth.put("username", "admin");
        auth.put("password", "password123");

        token =

                given()
                        .log().all()
                        .spec(SpecFactory.requestSpec())
                        .body(auth)

                        .when()
                        .post("/auth")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getString("token");

        String booking = """
                {
                    "firstname":"John",
                    "lastname":"Doe",
                    "totalprice":150,
                    "depositpaid":true,
                    "bookingdates":{
                        "checkin":"2025-07-01",
                        "checkout":"2025-07-05"
                    },
                    "additionalneeds":"Breakfast"
                }
                """;

        bookingId =

                given()
                        .log().all()
                        .spec(SpecFactory.requestSpec())
                        .body(booking)

                        .when()
                        .post("/booking")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getInt("bookingid");

        System.out.println("Token : " + token);
        System.out.println("Booking Id : " + bookingId);
    }

    @Test
    @Order(1)
    void invalidAuthentication() {

        Map<String, String> auth = new HashMap<>();
        auth.put("username", "admin");
        auth.put("password", "wrongPassword");

        given()
                .spec(SpecFactory.requestSpec())
                .body(auth)

                .when()
                .post("/auth")

                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    @Order(2)
    void invalidBooking() {

        given()

                .spec(SpecFactory.requestSpec())

                .when()

                .get("/booking/999999")

                .then()

                .statusCode(404);
    }

    @Test
    @Order(3)
    void partialUpdateBooking() {

        Response before =

                given()

                        .spec(SpecFactory.requestSpec())

                        .when()

                        .get("/booking/" + bookingId);

        JsonPath oldBooking = before.jsonPath();

        String body = """
                {
                    "firstname":"Michael"
                }
                """;

        Response response =

                given()

                        .log().all()

                        .spec(SpecFactory.authSpec(token))

                        .body(body)

                        .when()

                        .patch("/booking/" + bookingId);

        response.then().log().all();

        response.then().statusCode(200);

        JsonPath updated = response.jsonPath();

        assertEquals("Michael", updated.getString("firstname"));
        assertEquals(oldBooking.getString("lastname"), updated.getString("lastname"));
        assertEquals(oldBooking.getInt("totalprice"), updated.getInt("totalprice"));
        assertEquals(oldBooking.getBoolean("depositpaid"), updated.getBoolean("depositpaid"));
        assertEquals(oldBooking.getString("bookingdates.checkin"),
                updated.getString("bookingdates.checkin"));
        assertEquals(oldBooking.getString("bookingdates.checkout"),
                updated.getString("bookingdates.checkout"));
        assertEquals(oldBooking.getString("additionalneeds"),
                updated.getString("additionalneeds"));
    }

    @Test
    @Order(4)
    void deleteBooking() {

        given()

                .log().all()

                .spec(SpecFactory.authSpec(token))

                .when()

                .delete("/booking/" + bookingId)

                .then()

                .log().all()

                .statusCode(201);
    }

    @Test
    @Order(5)
    void verifyDeletion() {

        given()

                .spec(SpecFactory.requestSpec())

                .when()

                .get("/booking/" + bookingId)

                .then()

                .statusCode(404);
    }
}