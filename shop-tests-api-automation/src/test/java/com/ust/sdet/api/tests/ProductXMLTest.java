package com.ust.sdet.api.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ust.sdet.api.specification.CommonSpec.commonReqSpecXML;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class ProductXMLTest {
    @Test
    @DisplayName("M1: product detail matches product xml schema")
    void productListDetail_matchesXMLSchema() {
        given()
                .spec(commonReqSpecXML("/legacy/products"))
                .when()
                .get("/{id}.xml", 101)
                .then()
                .body(matchesXsdInClasspath("schemas/xsd/product.xsd"))
                .body("product.category", equalTo("Footwear"));

        //ContractAsserts.assertOkXMLContract(response, "schemas/xsd/product.xsd");
//        assertEquals("Footwear", response.path("product.category"));
    }
}
