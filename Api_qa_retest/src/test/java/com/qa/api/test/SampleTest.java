package com.qa.api.test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static com.qa.api.support.ProductFactory.*;
public class SampleTest {

    static Map<String,String> tok= Map.of("password","password123","username","admin");


    @Test
    @DisplayName("Post api auth test in restful-booker")
    void posttest()
    {
        given()
                .when()
                .spec(post)
                .body(tok)
                .post("")
                .then()
                .spec(ok).log().all()
                .body("token",notNullValue())
        ;

    }
    @Test
    @DisplayName("Get api booking test in restful-booker")
    void getest()
    {
        given()
                .when()
                .spec(get)
                .get("")
                .then()
                .spec(ok).log().all()
                .body("firstname",equalToIgnoringCase("uiii"))
                .body("lastname",equalToIgnoringCase("Subedi"))
                .body("totalprice",greaterThan(0))
        ;

    }}



