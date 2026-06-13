package com.ust.sdet.tests;

import com.ust.sdet.base.BaseApiTest;
import com.ust.sdet.specs.RequestSpecs;
import com.ust.sdet.specs.ResponseSpecs;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsTest extends BaseApiTest {

    @Test
    @DisplayName("Fetch products return 200")
    void getProducts_shouldReturn200() {
        Response response = given()
                .spec(RequestSpecs.jsonRequestSpec())
                .when()
                .get("/api/products")
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("items", not(empty()), "total", greaterThan(0))
                .body(matchesJsonSchemaInClasspath("schemas/json/products-schema.json"))
                .extract()
                .response();
        assertStatus(response, 200);
        assertResponseBody(response);
    }


    @Test
    @DisplayName("Fetch single product return 200")
    void getProductById_shouldReturn200() {
        Response response = given()
                .spec(RequestSpecs.jsonRequestSpec())
                .pathParam("id", 101)
                .when()
                .get("/api/products/{id}")
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("id", equalTo(101), "name", notNullValue(), "price", greaterThan(0))
                .body(matchesJsonSchemaInClasspath("schemas/json/product-schema.json"))
                .extract()
                .response();
        assertStatus(response, 200);
    }


    @Test
    @DisplayName("Search products should filtered result")
    void searchProduct_shouldReturn200() {
        given()
                .spec(RequestSpecs.jsonRequestSpec())
                .queryParam("search", "shoe")
                .when()
                .get("/api/products")
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("total", greaterThan(0))
                .body("items.name", hasItem("Running Shoes"));
    }


    @Test
    @DisplayName("Category filter return products")
    void categoryFilter_shouldReturn200() {
        given()
                .spec(RequestSpecs.jsonRequestSpec())
                .queryParam("category", "Fitness")
                .when()
                .get("/api/products")
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("items.category", everyItem(equalTo("Fitness")));
    }


    @Test
    @DisplayName("Invalid product id return 404")
    void invalidProduct_shouldReturn404() {
        Response response = given()
                .spec(RequestSpecs.jsonRequestSpec())
                .when()
                .get("/api/products/99999")
                .then()
                .spec(ResponseSpecs.notFound404Spec())
                .body("message", equalTo("Product not found"))
                .extract()
                .response();
        assertStatus(response, 404);
    }


    @Test

    @DisplayName("Legacy XML product return valid XML")
    void xmlProduct_shouldReturn200() throws Exception {
        String xml = given()
                .spec(RequestSpecs.xmlRequestSpec())
                .when()
                .get("/api/legacy/products/101.xml")
                .then()
                .spec(ResponseSpecs.xml200Spec())
                .extract()
                .asString();

        InputStream xsd = getClass().getClassLoader().getResourceAsStream("schemas/xsd/product-schema.xsd");
        assertNotNull(xsd);
        SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(new StreamSource(xsd))
                .newValidator()
                .validate(new StreamSource(new java.io.StringReader(xml)));
        assertTrue(xml.contains("<product>"));
    }


    @Test
    @DisplayName("Invalid XML product return 404")
    void xmlProductNotFound_shouldReturn404() {
        given()
                .spec(RequestSpecs.xmlRequestSpec())
                .when()
                .get("/api/legacy/products/99999.xml")
                .then()
                .statusCode(404)
                .body(containsString("Product not found"));
    }
}