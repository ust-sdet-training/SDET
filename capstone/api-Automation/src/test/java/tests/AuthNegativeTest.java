package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import specs.RequestSpec;
import utils.ConfigReader;

public class AuthNegativeTest {
    @Test
    void shouldRejectLoginWithWrongPassword(){
        Response response =
                given()
                        .spec(RequestSpec.request())
                        .body(
                                """
                                {
                                  "email":"wrong@test.com",
                                  "password":"wrongPassword"
                                }
                                """
                        )
                        .when()
                        .post(ConfigReader.get("auth.endpoint"))
                        .then()
                        .log().all()
                        .extract()
                        .response();
        assertEquals(401, response.statusCode());
    }

    @Test
    void shouldRejectRequestWithoutToken(){
        Response response =
                given()
                        .spec(RequestSpec.request())
                        .when()
                        .get(
                                ConfigReader.get("booking.endpoint")
                        )
                        .then()
                        .log().all()
                        .extract()
                        .response();
        assertEquals(401, response.statusCode());
    }
}