package services;

import config.ConfigReader;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthService {

    public String generateToken() {

        Response response =
                given()
                        .header(
                                "Content-Type",
                                "application/x-www-form-urlencoded; charset=UTF-8"
                        )

                        .auth()
                        .preemptive()
                        .basic(
                                ConfigReader.CLIENT_ID,
                                ConfigReader.CLIENT_SECRET
                        )

                        .formParam(
                                "grant_type",
                                "client_credentials"
                        )

                        .when()
                        .post(
                                ConfigReader.BASE_URL +
                                        "/api/oauth/token"
                        );

        System.out.println("Status: " + response.statusCode());
        System.out.println(response.asPrettyString());

        return response.jsonPath()
                .getString("access_token");
    }
}