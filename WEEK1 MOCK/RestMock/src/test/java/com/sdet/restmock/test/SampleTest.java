package com.sdet.restmock.test;
import com.sdet.restmock.config.BaseConfig;
import com.sdet.restmock.config.TestEnvironment.*;
import com.sdet.restmock.support.ProductFactory.*;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import static org.hamcrest.Matchers.*;
import static com.sdet.restmock.config.BaseConfig.BASE_URL;
import static io.restassured.RestAssured.*;
import static io.restassured.specification.ProxySpecification.auth;
import static com.sdet.restmock.support.ProductFactory.*;
public class SampleTest {

    public static Map order=Map.of("id",0,"petId","0","quantity","0","shipDate","2026-06-23T12:27:29.602Z","status","placed","complete","true");
    @Test
    @DisplayName("200 ok json")
    void okjsom()
    {
        given()
                .when().log().all()
                .spec(get)
                .get("")
                .then()
                .spec(ok);

    }
    @Test
    @DisplayName("post in api")
    void postin()
    {
        given()
                .spec(p)
                .log().all()
                .when()
                .body(order)
                .log().all()
                .post("")
                .then()
                .body("id",equalTo(0))
                .log().all();
    }
}
