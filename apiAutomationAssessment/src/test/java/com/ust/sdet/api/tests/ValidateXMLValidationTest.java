//package com.ust.sdet.api.tests;
//
//import io.restassured.path.xml.XmlPath;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static com.ust.sdet.api.support.SpecFactory.commonJsonRequest;
//import static io.restassured.RestAssured.given;
//import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//
//public class ValidateXMLValidationTest {
//    @Test
//    @DisplayName("Validate XML product details")
//    void xml_product_details() {
//
//        Response response =
//                given()
//                        .spec(commonJsonRequest)
//                        .when()
//                        .get("legacy/products/101.xml")
//                        .then()
//                        .statusCode(200)
//                        .body(matchesXsdInClasspath("schemas/xsd/product.xsd"))
//                        .body("product.id", equalTo("101"))
//                        .body("product.name", equalTo("Running Shoes"))
//                        .body("product.category", equalTo("Footwear"))
//                        .body("product.price", equalTo("4499"))
//                        .extract()
//                        .response();
//
//        XmlPath xml = response.xmlPath();
//
//        assertThat(xml.getInt("product.id"), equalTo(101));
//        assertThat(xml.getString("product.name"),equalTo("Running Shoes"));
//        assertThat(xml.getString("product.category"),equalTo("Footwear"));
//        assertThat(xml.getString("product.price"),equalTo("4499"));
//    }
//}
