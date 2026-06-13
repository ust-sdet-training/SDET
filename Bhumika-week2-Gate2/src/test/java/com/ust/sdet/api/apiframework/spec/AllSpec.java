package com.ust.sdet.api.apiframework.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import com.ust.sdet.api.apiframework.auth.TokenManager;
import com.ust.sdet.api.apiframework.config.ConfigManager;

public class AllSpec {


        public static RequestSpecification secureOrdersSpec() {
            return new RequestSpecBuilder()
                    .setBaseUri(ConfigManager.BASE_URL)
                    .setBasePath("/api/secure/orders")
                    .setAuth(oauth2(TokenManager.getOpsToken()))
                    .build();
        }


        public static RequestSpecification jsonSpec(String basePath) {

            return new RequestSpecBuilder()
                    .setBaseUri(ConfigManager.BASE_URL)//for DB this line
                    .setBasePath(basePath)
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .build();
        }

        public static RequestSpecification authSpec(
                String basePath,
                String token) {

            return new RequestSpecBuilder()
                    .setBasePath(basePath)
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .addHeader("Authorization",
                            "Bearer " + token)
                    .build();
        }

        //response
        public static ResponseSpecification okJson() {

            return new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .expectContentType(ContentType.JSON)
                    .build();
        }

        public static ResponseSpecification createdJson() {

            return new ResponseSpecBuilder()
                    .expectStatusCode(201)
                    .expectContentType(ContentType.JSON)
                    .build();
        }

        public static ResponseSpecification noContent() {

            return new ResponseSpecBuilder()
                    .expectStatusCode(204)
                    .build();
        }

    public static RequestSpecification secureOrders(String token) {

        return given()
                .baseUri(ConfigManager.BASE_URL)
                .basePath("/api/secure/orders")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token);
    }
    public static RequestSpecification userOrders(String token) {

        return given()
                .baseUri(ConfigManager.BASE_URL)
                .basePath("/api/orders")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token);
    }
    }


