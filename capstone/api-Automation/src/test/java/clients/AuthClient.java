package clients;


import io.restassured.response.Response;
import spec.RequestSpec;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;


public class AuthClient {


    public String login(){
        Response response =
                given()
                        .spec(RequestSpec.request())
                        .log().all()

                        .body(
                                """
                                {
                                  "email": "%s",
                                  "password": "%s"
                                }
                                """.formatted(
                                        ConfigReader.get("email"),
                                        ConfigReader.get("password")
                                )
                        )
                        .when()
                        .post(
                                ConfigReader.get("auth.endpoint"))
                        .then()
                        .log().all()
                        .extract()
                        .response();
        return response.jsonPath()
                .getString("token");
    }
}