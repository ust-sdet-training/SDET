package security;

import builders.LoginRequestBuilder;
import clients.AuthClient;
import config.SecretsManager;
import constants.ApiEndpoints;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.SpecFactory;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthorizationSecurityTest {

    private static String token;

    @BeforeAll
    static void setup() {

        RestAssured.requestSpecification =
                SpecFactory.getRequestSpec();

        token =
                new AuthClient()

                        .login(

                                LoginRequestBuilder

                                        .login()

                                        .email(
                                                SecretsManager.email()
                                        )

                                        .password(
                                                SecretsManager.password()
                                        )

                                        .build()

                        )

                        .token();

    }



    @Test
    @DisplayName("Pay Booking - Invalid Booking UUID")
    void payBookingWithInvalidBookingId() {

        given()

                .spec(
                        SpecFactory.getAuthRequestSpec(token)
                )

                .pathParam(
                        "bookingId",
                        UUID.randomUUID().toString()
                )

                .when()

                .post(
                        ApiEndpoints.PAY_BOOKING
                )

                .then()

                .log().all()

                .statusCode(404);

    }



    @Test
    @DisplayName("Confirm Booking - Invalid Booking UUID")
    void confirmBookingWithInvalidBookingId() {

        given()

                .spec(
                        SpecFactory.getAuthRequestSpec(token)
                )

                .pathParam(
                        "bookingId",
                        UUID.randomUUID().toString()
                )

                .when()

                .post(
                        ApiEndpoints.CONFIRM_BOOKING
                )

                .then()

                .log().all()

                .statusCode(404);

    }



    @Test
    @DisplayName("Get Booking - Invalid PNR")
    void getBookingWithInvalidPnr() {

        given()

                .spec(
                        SpecFactory.getAuthRequestSpec(token)
                )

                .pathParam(
                        "pnr",
                        "TS-1015-9999"
                )

                .when()

                .get(
                        ApiEndpoints.GET_BOOKING
                )

                .then()

                .log().all()

                .statusCode(404);

    }



    @Test
    @DisplayName("Get Booking - Random PNR")
    void getBookingWithRandomPnr() {

        given()

                .spec(
                        SpecFactory.getAuthRequestSpec(token)
                )

                .pathParam(
                        "pnr",
                        "INVALID-PNR"
                )

                .when()

                .get(
                        ApiEndpoints.GET_BOOKING
                )

                .then()

                .log().all()

                .statusCode(404);

    }



    @Test
    @DisplayName("Pay Booking - Invalid UUID Format")
    void payBookingWithInvalidUuidFormat() {

        given()

                .spec(
                        SpecFactory.getAuthRequestSpec(token)
                )

                .pathParam(
                        "bookingId",
                        "ABC123"
                )

                .when()

                .post(
                        ApiEndpoints.PAY_BOOKING
                )

                .then()

                .log().all()

                .statusCode(404)

                .body(
                        "error",
                        equalTo("NOT_FOUND")
                )

                .body(
                        "message",
                        equalTo("NOT_FOUND")
                );

    }

}