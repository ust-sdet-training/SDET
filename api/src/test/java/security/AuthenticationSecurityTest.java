package security;

import constants.ApiEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthenticationSecurityTest {

    @BeforeAll
    static void setup() {

        RestAssured.requestSpecification =
                SpecFactory.getRequestSpec();

    }


    @Test
    @DisplayName("Request without Authorization header")
    void requestWithoutAuthorizationHeader() {

        given()

                .body("""
                        {
                          "journeyType":"flight",
                          "inventoryId":"FL-COKBOM-51",
                          "seatIds":["20A"],
                          "refundable":true
                        }
                        """)

                .when()

                .post(ApiEndpoints.BOOKINGS)

                .then()

                .log().all()

                .statusCode(401);

    }



    @Test
    @DisplayName("Request with Invalid Token")
    void requestWithInvalidToken() {

        given()

                .header(
                        "Authorization",
                        "Bearer InvalidToken123"
                )

                .body("""
                        {
                          "journeyType":"flight",
                          "inventoryId":"FL-COKBOM-51",
                          "seatIds":["20A"],
                          "refundable":true
                        }
                        """)

                .when()

                .post(ApiEndpoints.BOOKINGS)

                .then()

                .log().all()

                .statusCode(401);

    }



    @Test
    @DisplayName("Request with Empty Token")
    void requestWithEmptyToken() {

        given()

                .header(
                        "Authorization",
                        "Bearer "
                )

                .body("""
                        {
                          "journeyType":"flight",
                          "inventoryId":"FL-COKBOM-51",
                          "seatIds":["20A"],
                          "refundable":true
                        }
                        """)

                .when()

                .post(ApiEndpoints.BOOKINGS)

                .then()

                .log().all()

                .statusCode(401);

    }



    @Test
    @DisplayName("Request with Malformed Token")
    void requestWithMalformedToken() {

        given()

                .header(
                        "Authorization",
                        "Bearer abc.def"
                )

                .body("""
                        {
                          "journeyType":"flight",
                          "inventoryId":"FL-COKBOM-51",
                          "seatIds":["20A"],
                          "refundable":true
                        }
                        """)

                .when()

                .post(ApiEndpoints.BOOKINGS)

                .then()

                .log().all()

                .statusCode(401);

    }



    @Test
    @DisplayName("Verify Unauthorized Error Response")
    void verifyUnauthorizedResponseBody() {

        Response response =

                given()

                        .header(
                                "Authorization",
                                "Bearer InvalidToken"
                        )

                        .body("""
                                {
                                  "journeyType":"flight",
                                  "inventoryId":"FL-COKBOM-51",
                                  "seatIds":["20A"],
                                  "refundable":true
                                }
                                """)

                        .when()

                        .post(ApiEndpoints.BOOKINGS);




        response

                .then()

                .log().all()

                .statusCode(401)

                .body(
                        "error",
                        equalTo("unauthorized")
                );

    }

}