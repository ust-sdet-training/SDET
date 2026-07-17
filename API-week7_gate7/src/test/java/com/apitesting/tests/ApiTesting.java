package com.apitesting.tests;

import com.apitesting.config.Config;
import com.apitesting.support.DBConnection;
import com.apitesting.support.DataBase;
import com.apitesting.support.Report;
import com.apitesting.support.builders.ApiSpecBuilders;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ApiTesting {

        public static String tokenGenerate() {

            Report.step("Generating auth token");

            Map<String, String> map = new LinkedHashMap<>();
            map.put("email", Config.email);
            map.put("password", Config.password);

        return given()
                .spec(ApiSpecBuilders.reqSpecpost())
                .body(map)
                .when()
                .post("/auth/login")
                .then()
                .spec(ApiSpecBuilders.resSpecpost())
                .extract()
                .path("token");

}

        @Test
        @DisplayName("GET the using auth token")
        void getFlight() {

            Report.step("GetFlight");

            Response res=
            given()
                    .spec(ApiSpecBuilders.reqSpecget(tokenGenerate()))
                    .when()
                    .get("/flights?from=DEL&to=BLR")
                    .then()
                    .spec(ApiSpecBuilders.resSpecget())
                            .body("from", not(emptyString()))
                            .body("to", not(emptyString()))
                            .body("from", equalTo("DEL"))
                            .body("to", equalTo("BLR"))
                            .body("class",equalTo("economy") )
                            .body("count",equalTo(48))
                            .body("pax", equalTo(1))
                    .extract()
                    .response();


            Report.info("Response", res.asPrettyString());
        }

    @Test
    @DisplayName("GET the using auth token")
    void getBooking() {

        Report.step("Get Booking");

        Response res=
                given()
                        .spec(ApiSpecBuilders.reqSpecget(tokenGenerate()))
                        .when()
                        .get("/bookings")
                        .then()
                        .spec(ApiSpecBuilders.resSpecget())
                        .extract()
                        .response();


        Report.info("Response", res.asPrettyString());
    }

//    @Test
//    void verifyContainer() {
//
//        System.out.println(DataBase.mysql.getJdbcUrl());
//        System.out.println(DataBase.mysql.getUsername());
//        assertNotNull(DataBase.mysql.getJdbcUrl());
//        assertNotNull(DataBase.mysql.getUsername());
//
//    }
//
//        @Test
//        void verifyDatabaseConnection() throws Exception {
//
//            Connection con = DBConnection.getConnection();
//
//            assertNotNull(con);
//            assertTrue(con.isValid(5));
//        }



}
