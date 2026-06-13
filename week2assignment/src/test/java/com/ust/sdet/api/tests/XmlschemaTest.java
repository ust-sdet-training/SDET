package com.ust.sdet.api.tests;



import com.ust.sdet.api.specs.SpecFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class XmlschemaTest {

    @Test
    @DisplayName("Validate XML product details")
    void xmlProductDetails() {

        Response response =
                given()
                        .spec(SpecFactory.req())
                        .when()
                        .get("/api/legacy/products/101.xml")
                        .then()
                        .statusCode(200)
                        .body(matchesXsdInClasspath("xsd/product.xsd"))
                        .body("product.id", equalTo("101"))
                        .body("product.name", equalTo("Running Shoes"))
                        .body("product.category", equalTo("Footwear"))
                        .body("product.price", equalTo("4499"))
                        .extract()
                        .response();

        System.out.println(response.asPrettyString());
    }
}