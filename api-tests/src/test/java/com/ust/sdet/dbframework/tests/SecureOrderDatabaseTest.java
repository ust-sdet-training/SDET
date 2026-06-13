package com.ust.sdet.dbframework.tests;

import com.ust.sdet.auth.Auth;

import com.ust.sdet.base.BaseDatabaseTest;

import com.ust.sdet.dbframework.model.OrderRow;

import com.ust.sdet.specs.RequestSpecs;

import com.ust.sdet.specs.ResponseSpecs;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;

import static org.junit.jupiter.api.Assertions.*;

public class SecureOrderDatabaseTest
        extends BaseDatabaseTest {

    @Test

    @DisplayName(
            "Allocate and ship secure order and validate database"
    )

    void secureOrderFlowShouldUpdateDatabase()
            throws Exception {

        int orderId =

                given()

                        .spec(

                                RequestSpecs
                                        .authenticatedSpec(
                                                Auth.oauthToken()
                                        )

                        )

                        .body(
                                """
                                {
                                  "items":[101]
                                }
                                """
                        )

                        .when()

                        .post(
                                "/api/secure/orders"
                        )

                        .then()

                        .spec(
                                ResponseSpecs.created201Spec()
                        )

                        .extract()

                        .path(
                                "id"
                        );



        given()

                .spec(

                        RequestSpecs
                                .authenticatedSpec(
                                        Auth.oauthToken()
                                )

                )

                .when()

                .post(
                        "/api/secure/orders/{id}/allocate",
                        orderId
                )

                .then()

                .spec(
                        ResponseSpecs.ok200Spec()
                )

                .body(

                        "status",

                        equalTo(
                                "ALLOCATED"
                        )

                );



        given()

                .spec(

                        RequestSpecs
                                .authenticatedSpec(
                                        Auth.oauthToken()
                                )

                )

                .when()

                .post(
                        "/api/secure/orders/{id}/ship",
                        orderId
                )

                .then()

                .spec(
                        ResponseSpecs.ok200Spec()
                )

                .body(

                        "status",

                        equalTo(
                                "SHIPPED"
                        )

                );


        OrderRow order =

                db.findOrder(
                        orderId
                );


        assertNotNull(
                order
        );

        assertEquals(

                "SHIPPED",

                order.getStatus()

        );

    }

}