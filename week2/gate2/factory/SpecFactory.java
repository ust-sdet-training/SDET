package week2.gate2.factory;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class SpecFactory {
    private static final String BASE_URL = System.getProperty(
            "baseUrl",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4000")
    );

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    public static String getToken(String id, String secret){
        return given()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .auth().preemptive().basic(id,secret)
                .formParam("grant_type","client_credentials")
                .when()
                .post("/oauth/token")
                .then()
                .statusCode(200)
                .body("token_type", equalToIgnoringCase("Bearer"))
                .extract()
                .path("access_token");
    }



    public static RequestSpecification authedOrders(String token){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/secure/orders")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2(token))
                .build();
    }


    public static ResponseSpecification okJson = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification unauthenticatedJson = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(401)
            .build();

    public static ResponseSpecification unauthorizedJson = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(403)
            .build();

    public static ResponseSpecification notFoundJson = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(404)
            .build();

    public static RequestSpecification cartSpec(String token) {
        return new RequestSpecBuilder()
                .setBasePath("/api/cart")
                .addHeader("Authorization", "Bearer " + token)
                .setContentType(ContentType.JSON)
                .build();
    }


    public static RequestSpecification orderSpec(String token){
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .setBasePath("/api/orders")
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification legacyProductsXml(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/legacy/products")
                .setAccept(ContentType.XML)
                .log(LogDetail.URI)
                .build();
    }
}


