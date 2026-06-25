package tests;

import utils.BaseTest;
import io.restassured.http.ContentType;
import models.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.SpecFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class UserApiTest extends BaseTest {

    @Test
    @DisplayName("Creating and Updating the User")
    void createGetAndUpdateUser() {

        Map<String, Object> userBody = Map.of(
                "id", 1,
                "username", "saiteja",
                "firstName", "Sai",
                "lastName", "Teja",
                "email", "sai@test.com",
                "password", "test123",
                "phone", "9999999999",
                "userStatus", 1
        );

        given()
                .spec(SpecFactory.reqSpec())
                .body(userBody)

                .when()
                .post(SpecFactory.userURL())

                .then()
                .spec(SpecFactory.successResponseSpec());

        UserResponse user =
                given()
                        .pathParam("username", "saiteja")

                        .when()
                        .get(SpecFactory.userByNameURL())

                        .then()
                        .spec(SpecFactory.successResponseSpec())
                        .extract()
                        .as(UserResponse.class);

        assertEquals("saiteja", user.username());
        assertEquals("Sai", user.firstName());

        Map<String, Object> updateBody = Map.of(
                "id", 1,
                "username", "saiteja",
                "firstName", "Sai Teja Updated",
                "lastName", "Teja",
                "email", "sai@test.com",
                "password", "test123",
                "phone", "9999999999",
                "userStatus", 1
        );

        given()
                .spec(SpecFactory.reqSpec())
                .body(updateBody)

                .when()
                .put("/user/saiteja")

                .then()
                .spec(SpecFactory.successResponseSpec());
    }

    @Test
    @DisplayName("Logging in the Created User")
    void loginUser() {

        given()
                .queryParam("username", "saiteja")
                .queryParam("password", "test123")

                .when()
                .get(SpecFactory.loginURL())

                .then()
                .spec(SpecFactory.successResponseSpec());
    }

    @Test
    @DisplayName("Logging out the Created User")
    void loginOutUser() {

        given()

                .when()
                .get(SpecFactory.logoutURL())

                .then()
                .spec(SpecFactory.successResponseSpec());
    }
}