package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class apiTest {

    @Test
    void loginTest(){

        Response response =
                given()
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .body(
                                Map.of(
                                        "email",
                                        "dave@tripstack.test",
                                        "password",
                                        "Password@123"
                                )
                        )
                        .when()
                        .post("/api/auth/login")
                        .then()
                        .log()
                        .all()
                        .extract()
                        .response();

        String token =
                response.jsonPath()
                        .getString("token");
        System.out.println(
                "Token : " + token
        );
    }
}