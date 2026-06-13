package ust.sdet.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;


public class Headers {

    static final String BASE_URL=System.getProperty("baseURL",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4000"));


    static RequestSpecification authedReqSpec(String token){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/secure/orders")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(token))
                .build();
    }

    public static RequestSpecification legacyproducts(){

        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/legacy/products")
                .setAccept(ContentType.XML)
                .build();

    }

    public static void assertOkXmlContract(Response response, String schemaPath){

        response.then()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body(matchesXsdInClasspath(schemaPath));

    }

}
