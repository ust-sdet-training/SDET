package com.ust.sdet.tests;

import com.ust.sdet.config.ApiConfig;
import com.ust.sdet.model.postModel;
import com.ust.sdet.specs.SpecFactory;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostTest{

    @BeforeEach
    void setup(){
        RestAssured.baseURI = ApiConfig.BASE_URL;
    }
    @Test
    @DisplayName("GET: Validating the details using Junit Assertions")
    void getDetails(){

        postModel pos =
                given()
                        .spec(SpecFactory.reqspec())
                        .when()
                        .log().all()
                        .get("/posts/1")
                        .then()
                        .spec(SpecFactory.resspec())
                        .extract()
                        .as(postModel.class);

        assertEquals(1,pos.userId());
        assertEquals(1,pos.id());
        assertTrue(pos.title().contains("sunt aut"));
        assertTrue(pos.body().contains("molestiae"));


    }

    @Test
    @DisplayName("GET: Validating the details using RestAssured Assertions")
    void getDetails1(){

                given()
                        .spec(SpecFactory.reqspec())
                        .when()
                        .log().all()
                        .get("/posts/1")
                        .then()
                        .spec(SpecFactory.resspec())
                                .body("userId",equalTo(1))
                        .body("id",equalTo(1))
                        .body("title",notNullValue())
                        .body("body",notNullValue())
                        .body("title",containsString("sunt aut"))
                        .body("body",containsString("molestiae"));

    }


}
