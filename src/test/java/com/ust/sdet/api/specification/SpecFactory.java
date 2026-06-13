package com.ust.sdet.api.specification;

import com.ust.sdet.api.support.Credentials;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.ust.sdet.api.support.Credentials.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

public class SpecFactory {

    public static String token() {
        return given()
                .baseUri(BASE_URL)
                .basePath("/api")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .accept(ContentType.JSON)
                .formParam("grant_type", "client_credentials")
                .auth().preemptive().basic(Credentials.OAUTH_CLIENT_ID, Credentials.OAUTH_CLIENT_SECRET)
                .when()
                .post("/oauth/token")
                .then()
                .body("access_token", notNullValue())
                .extract()
                .jsonPath()
                .getString("access_token");
    }

    static String viewerToken() {
        return given()
                .baseUri(BASE_URL)
                .basePath("/api")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .accept(ContentType.JSON)
                .formParam("grant_type", "client_credentials")
                .auth().preemptive().basic(Credentials.OAUTH_VIEWER_CLIENT_ID, Credentials.OAUTH_VIEWER_CLIENT_SECRET)
                .when()
                .post("/oauth/token")
                .then()
                .body("access_token", notNullValue())
                .extract()
                .jsonPath()
                .getString("access_token");
    }



    static String invalidToken() {
        return given()
                .baseUri(BASE_URL)
                .basePath("/api")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .formParam("grant_type", "client_credentials")
                .auth().preemptive().basic(Credentials.OAUTH_EXPIRED_CLIENT_ID, Credentials.OAUTH_EXPIRED_CLIENT_SECRET)
                .when()
                .post("/oauth/token")
                .then()
                .body("access_token", notNullValue())
                .extract()
                .path("access_token")
                .toString(  );
    }

    public static RequestSpecification loginSpec(String path){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api"+path)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification reqSpec(String path, String pToken) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api"+ path)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2(pToken))
                .build();

    }

    public static ResponseSpecification resSpec () {
        return  new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }

    public static ResponseSpecification orderResSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }

    public static ResponseSpecification cartResSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(409)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }


    public static RequestSpecification apiKeyReqSpec(String path) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api"+ path)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("X-API-Key", RETAIL_API_KEY)
                .build();

    }

    static RequestSpecification negativeReqSpec(String path, String pToken) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api"+ path)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2(pToken))
                .build();

    }


    public static RequestSpecification authedOrders(String path, String ptoken){
        return new io.restassured.builder.RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api" + path)
                .setAccept(io.restassured.http.ContentType.JSON)
                .setAuth(io.restassured.RestAssured.oauth2(ptoken))
                .build();
    }

    public static RequestSpecification alloReqSpec(String path, int id, String pToken) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api"+ path +"/"+id+"/allocate")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2(pToken))
                .build();

    }

    public static RequestSpecification shipReqSpec(String path, int id, String pToken) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api"+ path +"/"+id+"/ship")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2(pToken))
                .build();

    }

    public static ResponseSpecification notAlloResSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(409)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }
}
