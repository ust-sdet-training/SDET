package security;

import constants.ApiEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class SecurityHeadersTest {

    @BeforeAll
    static void setup() {

        RestAssured.requestSpecification =
                SpecFactory.getRequestSpec();

    }



    @Test
    @DisplayName("Verify X-Content-Type-Options Header")
    void verifyContentTypeOptionsHeader() {

        Response response =

                given()

                        .when()

                        .post(ApiEndpoints.LOGIN)

                        .then()

                        .extract()

                        .response();

        assertThat(
                response.getHeader(
                        "X-Content-Type-Options"
                ),
                notNullValue()
        );

    }



    @Test
    @DisplayName("Verify X-Frame-Options Header")
    void verifyFrameOptionsHeader() {

        Response response =

                given()

                        .when()

                        .post(ApiEndpoints.LOGIN)

                        .then()

                        .extract()

                        .response();

        assertThat(
                response.getHeader(
                        "X-Frame-Options"
                ),
                notNullValue()
        );

    }



    @Test
    @DisplayName("Verify Referrer-Policy Header")
    void verifyReferrerPolicyHeader() {

        Response response =

                given()

                        .when()

                        .post(ApiEndpoints.LOGIN)

                        .then()

                        .extract()

                        .response();

        assertThat(
                response.getHeader(
                        "Referrer-Policy"
                ),
                notNullValue()
        );

    }



    @Test
    @DisplayName("Verify Server Header Exists")
    void verifyServerHeader() {

        Response response =

                given()

                        .when()

                        .post(ApiEndpoints.LOGIN)

                        .then()

                        .extract()

                        .response();

        assertThat(
                response.getHeader(
                        "Server"
                ),
                notNullValue()
        );

    }

}