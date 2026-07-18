package security;

import builders.LoginRequestBuilder;
import clients.AuthClient;
import config.SecretsManager;
import constants.ApiEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasKey;

public class ResponseSecurityTest {

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
    @DisplayName("Response should not expose stack trace")
    void responseShouldNotExposeStackTrace() {

        Response response =

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
                        );

        String body =
                response.getBody().asString();

        assertThat(
                body,
                not(
                        containsString("Exception")
                )
        );

        assertThat(
                body,
                not(
                        containsString("java.")
                )
        );

        assertThat(
                body,
                not(
                        containsString("org.springframework")
                )
        );

    }



    @Test
    @DisplayName("Error response should contain error field")
    void errorResponseContainsErrorField() {

        Response response =

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
                        );

        response

                .then()

                .statusCode(404);

        assertThat(
                response.jsonPath().getMap("$"),
                hasKey("error")
        );

    }



    @Test
    @DisplayName("Error response should contain message field")
    void errorResponseContainsMessageField() {

        Response response =

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
                        );

        response

                .then()

                .statusCode(404);

        assertThat(
                response.jsonPath().getMap("$"),
                hasKey("message")
        );

    }

}