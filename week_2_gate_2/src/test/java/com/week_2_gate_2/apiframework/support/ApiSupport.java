package com.week_2_gate_2.apiframework.support;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import static com.week_2_gate_2.apiframework.config.Apiconfig.BASE_URL;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;


public class ApiSupport {

    public static ResponseSpecification okJson = new ResponseSpecBuilder()
                                                .expectStatusCode(200)
                                                .expectContentType(ContentType.JSON)
                                                .expectResponseTime(lessThan(800L))
                                                .build();
    
    static ResponseSpecification deleteJson = new ResponseSpecBuilder()
                                                    .expectStatusCode(204)
                                                    .expectResponseTime(lessThan(800L))
                                                    .build();
    
    public static ResponseSpecification postJson = new ResponseSpecBuilder()
                                                .expectStatusCode(201)
                                                .expectContentType(ContentType.JSON)
                                                .expectResponseTime(lessThan(800L))
                                                .build();

    public static ResponseSpecification res404 = new ResponseSpecBuilder()
                                                .expectStatusCode(201)
                                                .expectContentType(ContentType.JSON)
                                                .expectResponseTime(lessThan(800L))
                                                .build();
    
                                                
    public static ResponseSpecification res401 = new ResponseSpecBuilder()
                                                .expectStatusCode(401)
                                                .expectContentType(ContentType.JSON)
                                                .expectResponseTime(lessThan(800L))
                                                .build();

    public static ResponseSpecification res409 = new ResponseSpecBuilder()
                                                .expectStatusCode(409)
                                                .expectContentType(ContentType.JSON)
                                                .expectResponseTime(lessThan(800L))
                                                .build();

    public static RequestSpecification xmlRequest (){
                                            return new RequestSpecBuilder()
                                                    .setBaseUri("http://localhost:4000")
                                                    .setBasePath("/api/legacy/products")
                                                    .setContentType(ContentType.XML)
                                                    .build();
}
                                           

    public static RequestSpecification authed(String token) {
        return new  RequestSpecBuilder()
                        .setBaseUri(BASE_URL)
                        .setBasePath("/api")
                        .setContentType(ContentType.JSON)
                        .setAccept(ContentType.JSON)
                        .setAuth(oauth2(token))
                        .build();
    }
    public static RequestSpecification notAuthed=
        new  RequestSpecBuilder()
                        .setBaseUri(BASE_URL)
                        .setBasePath("/api")
                        .setContentType(ContentType.JSON)
                        .setAccept(ContentType.JSON)
                        .build();
    
    public static RequestSpecification apiAuth(String API_KEY) {
        return new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setBasePath("/api")
                    .setContentType(ContentType.JSON)
                    .setAccept(ContentType.JSON)
                    .addHeader("x-api-key",API_KEY)
                    .build();
    }

    public static String Fetch(String userName,String password){
        return given()
                        .baseUri(BASE_URL)
                        .basePath("/api")
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .auth().preemptive().basic(userName, password)
                        .formParam("grant_type", "client_credentials")
                    .when()
                        .post("/oauth/token")
                    .then()
                        .statusCode(200)
                        .extract()
                        .path("access_token");  
    }

}
