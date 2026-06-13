package ust.sdet.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ust.sdet.db.config.TestEnvironment;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.emptyOrNullString;

public class Authorization {

    static final String BASE_URL=System.getProperty("baseURL",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4000"));



    static String fetchToken(String client_id, String client_secret){


        return
                given()
                        .baseUri(BASE_URL)
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .auth().preemptive().basic(client_id, client_secret)
                        .formParam("grant_type", "client_credentials")
                        .when()
                        .post("/api/oauth/token")
                        .then()
                        .statusCode(200)
                        .body("token_type", equalToIgnoringCase("Bearer"))
                        .body("expires_in", greaterThan(0))
                        .body("access_token", not(emptyOrNullString()))
                        .extract()
                        .path("access_token");
    }
    static String fetchNegativeToken(String client_id, String client_secret){
        return
                given()
                        .baseUri(BASE_URL)
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .auth().preemptive().basic(client_id, client_secret)
                        .formParam("grant_type", "client_credentials")
                        .when()
                        .post("/api/oauth/token")
                        .then()
                        .statusCode(200)
                        .body("token_type", equalToIgnoringCase("Bearer"))
                        .extract()
                        .path("access_token");
    }

    public static String LoginOpsUser(){
        return fetchToken(TestEnvironment.required("OAUTH_CLIENT_ID"),TestEnvironment.required("OAUTH_CLIENT_SECRET"));
    }
    public static String LoginExpiredUser(){
        return fetchNegativeToken(TestEnvironment.required("OAUTH_EXPIRED_CLIENT_ID"),TestEnvironment.required("OAUTH_EXPIRED_CLIENT_SECRET"));
    }
    public static String LoginViewerUser(){
        return fetchNegativeToken(TestEnvironment.required("OAUTH_VIEWER_CLIENT_ID"),TestEnvironment.required("OAUTH_VIEWER_CLIENT_SECRET"));
    }

    public static RequestSpecification authedReqSpec(String token){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/secure/orders")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(token))
                .build();
    }
}
