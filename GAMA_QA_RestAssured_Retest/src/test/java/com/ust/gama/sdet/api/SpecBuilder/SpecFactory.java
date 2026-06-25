package com.ust.gama.sdet.api.SpecBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;
import static org.hamcrest.Matchers.lessThan;

public class SpecFactory {

    public static final String API_BASEURL = System.getProperty(
            "easeMyTrip_URL",
            TestEnvCheck.optional("easeMyTrip_URL","https://www.easemytrip.com")
    );
    public static final String API_BASEURL_CURR = System.getProperty(
            "restful_broker",
            TestEnvCheck.optional("restful_broker","https://restful-booker.herokuapp.com")
    );
    public static final String USERNAME = TestEnvCheck.optional("username", "admin");
    public static final String PASSWORD = TestEnvCheck.optional("password", "password123");

    public static Map<String, String> credentialBody = Map.ofEntries(
            entry("username", "admin"),
            entry("password", "password123")
    );

    public static Map<String, Object> bookingDetails = Map.ofEntries(
            entry("firstname", "Jim"),
            entry("lastname", "Brown"),
            entry("totalprice", 111),
            entry("depositpaid", true),
            entry("bookingdates", Map.ofEntries(
                    entry("checkin", "2018-01-01"),
                    entry("checkout", "2019-01-01")
            )),
            entry("additionalneeds", "Breakfast")
    );




    @BeforeAll
    static void setup(){
        RestAssured.baseURI = API_BASEURL_CURR;
//        RestAssured.basePath = "/pet";
    }

    public static RequestSpecification reqSpecJSON(String path){
        return new RequestSpecBuilder()
                .setBaseUri(API_BASEURL_CURR)
                .setBasePath(path)
                .setContentType("application/json")
                .build();
    }
    public static ResponseSpecification bookingID_Status200(){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
//                .expectResponseTime(lessThan(800L))
                .build();
    }
    public static ResponseSpecification petStatusFailRespSpec(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }


    public static RequestSpecification petIDSuccessReqSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(API_BASEURL)
                .setBasePath("/pet")
                .setAccept(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification petIDSuccessRespSpec(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }




}

