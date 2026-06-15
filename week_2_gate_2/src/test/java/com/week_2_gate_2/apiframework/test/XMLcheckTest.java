package com.week_2_gate_2.apiframework.test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.week_2_gate_2.apiframework.support.ApiSupport.*;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

public class XMLcheckTest {

    @Test
    @DisplayName("M3: product detail matches product JSON schema")
    void schema_validation(){
    given()
            .spec(xmlRequest())
            .when()
            .get("/101.xml")
            .then()
            .body(matchesXsdInClasspath("schemas/xsd/xmlcheck.schema.xml"));
}

}

