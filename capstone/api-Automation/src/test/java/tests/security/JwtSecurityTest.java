package tests.security;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import spec.RequestSpec;
import utils.ConfigReader;

public class JwtSecurityTest {
    @Test
    void shouldRejectTamperedJWT(){
        Response response =
                given()
                        .spec(RequestSpec.request())
                        .header(
                                "Authorization",
                                "Bearer invalid.jwt.token"
                        )
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