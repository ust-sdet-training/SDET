package com.ust.sdet.api.dbframework.test;

import com.ust.sdet.api.dbframework.config.DatabaseConfig;
import com.ust.sdet.api.dbframework.model.OrderRow;
import com.ust.sdet.api.dbframework.support.DbSupport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.apiframework.auth.apiConfig.LoginToken;
import static com.ust.sdet.api.apiframework.spec.SpecFactory.authed;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class DBTest {
    static DbSupport database;

    @BeforeAll
    static void setup()
    {

        database = new DbSupport(DatabaseConfig.fromEnvironment());
    }

    @Test
    @DisplayName("Local M1: MySQL is reachable through JDBC")
    void localMysqlIsReachable() throws Exception
    {
        assertTrue(database.isReachable());
    }


    static String accessToken;

    @BeforeAll
    static void Before()
    {
        accessToken  = LoginToken()  ;
    }

    @Test
    @DisplayName("Persisted and test")
    void createOrder_isPersisted() throws SQLException {
        var order= Map.of("items", List.of(101,107),"currency","INR");

        var c =
                given()
                        .spec(authed(accessToken))
                        .body(order)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .extract();

        System.out.println(c);

        Integer id = c.path("orderId");
        OrderRow  row = DbSupport.findOrder(id);
        assertNotNull(row, "order must be persisted");
        assertEquals("CREATED", row.status());
        assertEquals(0,row.total().compareTo(c.jsonPath()
                .getObject("total", BigDecimal.class)));

    }

}
