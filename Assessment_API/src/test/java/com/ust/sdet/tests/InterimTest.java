package com.ust.sdet.tests;

import com.ust.sdet.specfactory.RequestSpec;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.ust.sdet.specfactory.RequestSpec.fetchToken;
import static io.restassured.RestAssured.given;

public class InterimTest {

public  static String token;

    @BeforeAll
    static void setup(){
        token = fetchToken("admin","password123");
    }

    @Test
    @DisplayName("Test 1")
    void api(){
      int bookingid  = given().spec(RequestSpec.post(token))
                .when().post().then().statusCode(200).extract().path("id");

        given().spec(RequestSpec.post(token))
                .body("""
                        {
                          ""firstname"": ""James"",
                          ""lastname"": ""Smith"",
                          ""totalprice"": 500,
                          ""depositpaid"": false,
                          ""bookingdates"": {
                            ""checkin"": ""2026-09-01"",
                            ""checkout"": ""2026-09-06""
                          },
                          ""additionalneeds"": ""Lunch""
                        }
                        
                        """)
                .when().put("/{id}",bookingid)
                .then()
                .statusCode(200);
    }





}
