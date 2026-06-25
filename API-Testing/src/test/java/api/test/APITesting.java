package api.test;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import api.spec.SpecFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class APITesting {

    //String token;

    @Test
    void verifyHomePageReachable() {
        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/apidoc/index.html");
        response.then()
                .spec(SpecFactory.success200());
    }

    @Test
    void validateHeaders() {

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/apidoc/index.html");

        response.then()
                .spec(SpecFactory.success200());

        assertTrue(
                response.getHeader("Content-Type")
                        .contains("text/html")
        );
    }

    @Test
    void validateGetAPI() {
        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/booking");
                        response.then()
                        .log().all()
                        .spec(SpecFactory.responseSpec());

    }

    @Test
    void validatingGetById(){
        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .pathParam("id",1)
                        .when()
                        .get("/booking/{id}");
        response.then()
                .log().all()
                .spec(SpecFactory.responseSpec())
                .body("firstname",equalTo("Jim"));
    }

    @Test
    void tokenGeneration(){
         String token=
                given()
                        .spec(SpecFactory.requestSpec())
                        .body(Map.of(
                                "username" , "admin",
                                "password" , "password123"))
                        .when()
                        .post("/auth")
                        .then().log().all()
                        .spec(SpecFactory.success200())
                        .extract().toString();
    }
    @Test
    void validatingPost() {
        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .body(Map.of(
                                "firstname", "Jim",
                                "lastname", "Brown",
                                "totalprice", 111,
                                "depositpaid", true,
                                "bookingdates", Map.of(
                                        "checkin", "2018-01-01",
                                        "checkout", "2019-01-01"
                                ),
                                "additionalneeds", "Breakfast"
                        ))
                        .when()
                        .post("/booking");

        response.then()
                .log().all()
                .statusCode(200);
    }
    @Test
    void negativeTest403(){
        given()
                .spec(SpecFactory.requestSpec())
                .pathParam("id",1)
                .when()
                .delete("/booking/{id}")
                .then()
                .log().all()
                .statusCode(403);
    }
}