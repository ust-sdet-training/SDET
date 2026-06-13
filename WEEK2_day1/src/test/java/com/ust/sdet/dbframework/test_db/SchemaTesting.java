package com.ust.sdet.dbframework.test_db;

import static com.ust.sdet.Api_Framework.specs.SpecFactory.*;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.ust.sdet.Api_Framework.specs.SpecFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


//@ExtendWith(LocalBackendServer.class)
public class SchemaTesting {

    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;
    private static RequestSpecification xmlRequestSpec;
    private static ResponseSpecification xmlResponseSpec;

    private static final String BASE_URL = System.getProperty(
            "baseUrl",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4000")
    );

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

        requestSpec = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(1200L))
                .build();

        xmlRequestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api")
                .setAccept(ContentType.XML)
                .setContentType(ContentType.XML)
                .build();
        xmlResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.XML)
                .expectResponseTime(lessThan(1000L))
                .build();

    }

    @Test
    @DisplayName("M1: Product details matches with json")
    void productDetail_matchersJsonSchema(){
        given().spec(requestSpec)
                .pathParam("id",101)
                .when()
                .get(productById(101))
                .then()
                .spec(responseSpec)
                .body(matchesJsonSchemaInClasspath("schema/json/product.schema.json"));

    }

    @Test
    @DisplayName("M2: Product List details matches with json")
    void product_list_Detail_matchersJsonSchema(){
        given().spec(requestSpec)
                .when()
                .get(productsPath())
                .then()
                .spec(responseSpec)
                .body(matchesJsonSchemaInClasspath("schema/json/product-list.schema.json"));
    }

    @Test
    @DisplayName("M3: Legacy Product XML matches product XSD")
    void productXml_validation(){
        given()
                .spec(xmlRequestSpec)
                .when()
                .get(legacyProductXmlPath(101))
                .then()
                .spec(xmlResponseSpec)
                .body(matchesXsdInClasspath("schema/xsd/product.xsd"));

    }
}
