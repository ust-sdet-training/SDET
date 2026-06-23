package com.ust.sdet.tests;

import com.ust.sdet.specs.CommonSpecs;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.ust.sdet.specs.CommonSpecs.commonReqSpec;
import static com.ust.sdet.specs.CommonSpecs.commonResSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ScenarioTest {
    @Test
    @DisplayName("M1: GET /pets/findbystatus")
    void getPetsTest() {
        given()
                .spec(commonReqSpec())
                .queryParam("status", "available")
        .when()
                .get("/pet/findByStatus")
        .then()
                .spec(commonResSpec())
                .log().all()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("M2: POST /user")
    void postUserTest() {

        given()
                .spec(commonReqSpec())
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "id", 1,
                        "username", "testuser",
                        "firstName", "test",
                        "lastName", "user",
                        "email", "test@user.com",
                        "password", "test@user",
                        "phone", "9876543210",
                        "userStatus", 1))

        .when()
                .post("/user")
        .then()
                .spec(commonResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .body("code", equalTo(200));
    }

    @Test
    @DisplayName("M3: PUT /user/{username}")
    void updateUserTest() {
        given()
                .spec(commonReqSpec())
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "id", 1,
                        "username", "testuser",
                        "firstName", "updated",
                        "lastName", "user",
                        "email", "test@user.com",
                        "password", "test@user",
                        "phone", "9999999999",
                        "userStatus", 1))

                .when()
                .put("/user/{username}", "testuser")
                .then()
                .spec(commonResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .body("code", equalTo(200));

        given()
                .spec(commonReqSpec())
                .when()
                .get("/user/{username}", "testuser")
                .then()
                .spec(commonResSpec())
                .body("id", equalTo(1))
                .body("firstName", equalTo("updated"))
                .body("id", equalTo(1));
    }

    @Test
    @DisplayName("M4: DELETE /user/{username}")
    void deleteUserTest() {
        given()
                .spec(commonReqSpec())
                .when()
                .delete("/user/{username}", "testuser")
                .then()
                .spec(commonResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .body("message", equalTo("testuser"));
    }
}
