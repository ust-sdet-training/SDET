package services;

import config.ConfigReader;
import io.restassured.response.Response;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class AuthService {

    public static String getAccessToken() {

        String credentials = ConfigReader.CLIENT_ID + ":" + ConfigReader.CLIENT_SECRET;

        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

        Response response =
                given()
                        .header("Authorization", "Basic " + encoded)
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .formParam("grant_type", "client_credentials")
                        .log().all()
                        .when()
                        .post(ConfigReader.BASE_URL + "/api/oauth/token");


        return response.jsonPath()
                .getString("access_token");
    }
}