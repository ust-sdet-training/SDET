package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import model.UserResponse;
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
                "username", "Hemanth",
                "firstName", "Hemanth",
                "lastName", "Reddy",
                "email", "hemanthReddy@gmail.com",
                "password", "Hemu@123",
                "phone", "7306123456",
                "userStatus", 1
        );
        given()
                .contentType(ContentType.JSON)
                .body(userBody)
                .when()
                .post(SpecFactory.userURL())
                .then()
                .spec(SpecFactory.successResponseSpec());
        UserResponse user =
                given()
                        .pathParam("username", "Hemanth")
                        .when()
                        .get(SpecFactory.userByNameURL())
                        .then()
                        .spec(SpecFactory.successResponseSpec())
                        .extract()
                        .as(UserResponse.class);
        assertEquals("Hemanth", user.username());
        assertEquals("Hemanth", user.firstName());
        Map<String, Object> updateBody = Map.of(
                "id", 1,
                "username", "Hemanth",
                "firstName", "Hemanth Updated",
                "lastName", "Reddy",
                "email", "hemanthReddy@gmail.com",
                "password", "Hemu@123",
                "phone", "7306123456",
                "userStatus", 1
        );
        given()
                .spec(SpecFactory.reqSpec())
                .body(updateBody)
                .when()
                .put("/user/Hemanth")
                .then()
                .spec(SpecFactory.successResponseSpec());
    }
}