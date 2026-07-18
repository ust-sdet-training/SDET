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

import static io.restassured.RestAssured.given;

public class InputValidationSecurityTest {

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
    @DisplayName("Empty Request Body")
    void emptyRequestBody() {

        given()

                .spec(
                        SpecFactory.getAuthRequestSpec(token)
                )

                .body("{}")

                .when()

                .post(ApiEndpoints.BOOKINGS)

                .then()

                .log().all()

                .statusCode(400);

    }



    @Test
    @DisplayName("Missing Inventory Id")
    void missingInventoryId() {

        given()

                .spec(
                        SpecFactory.getAuthRequestSpec(token)
                )

                .body("""
                        {
                          "journeyType":"flight",
                          "seatIds":["20A"],
                          "refundable":true
                        }
                        """)

                .when()

                .post(ApiEndpoints.BOOKINGS)

                .then()

                .log().all()

                .statusCode(400);

    }



    @Test
    @DisplayName("Empty Seat List")
    void emptySeatList() {

        given()

                .spec(
                        SpecFactory.getAuthRequestSpec(token)
                )

                .body("""
                        {
                          "journeyType":"flight",
                          "inventoryId":"FL-COKBOM-51",
                          "seatIds":[],
                          "refundable":true
                        }
                        """)

                .when()

                .post(ApiEndpoints.BOOKINGS)

                .then()

                .log().all()

                .statusCode(400);

    }



    @Test
    @DisplayName("Null Seat List")
    void nullSeatList() {

        given()

                .spec(
                        SpecFactory.getAuthRequestSpec(token)
                )

                .body("""
                        {
                          "journeyType":"flight",
                          "inventoryId":"FL-COKBOM-51",
                          "seatIds":null,
                          "refundable":true
                        }
                        """)

                .when()

                .post(ApiEndpoints.BOOKINGS)

                .then()

                .log().all()

                .statusCode(400);

    }



    @Test
    @DisplayName("Invalid Journey Type")
    void invalidJourneyType() {

        given()

                .spec(
                        SpecFactory.getAuthRequestSpec(token)
                )

                .body("""
                        {
                          "journeyType":"car",
                          "inventoryId":"FL-COKBOM-51",
                          "seatIds":["20A"],
                          "refundable":true
                        }
                        """)

                .when()

                .post(ApiEndpoints.BOOKINGS)

                .then()

                .log().all()

                .statusCode(400);

    }

}