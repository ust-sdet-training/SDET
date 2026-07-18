package com.sdet.restmock.test;
import com.sdet.restmock.config.BaseConfig;
import com.sdet.restmock.config.UserData;
import com.sdet.restmock.support.ProductFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static com.sdet.restmock.config.UserData.*;
import static com.sdet.restmock.support.ProductFactory.*;

public class SampleTest {

    public static  String  Token;
    @BeforeAll
    @DisplayName("get the token and printing the token")
    static void getTheToken()
    {
            Token= ProductFactory.getAuthToken();

    }


    @Test
    @DisplayName("get the bus details and print the bus details")
    void getTheBusDetails()
    {
        String busid=given()
                .when()
                .spec(getbus)
                .queryParam("from", "HYD")
                .queryParam("to", "CCU")
                .queryParam("date", "2026-07-27")
                .get("")
                .then()
                .statusCode(200)
                .log().all()
                .body("count",notNullValue())
                .body("from", equalToIgnoringCase("HYD"))
                .body("to",equalToIgnoringCase("CCU"))
                .extract().path("buses[0].id");

        System.out.println(busid);


        given()
                .when()
                .spec(getbus)
                .pathParam("id",busid)
                .get("/{id}/seats")
                .then()
                .statusCode(200)
                .body("decks.lower", not(empty()))
                .body("decks.upper", not(empty()));

    }

    @Test
    @DisplayName("When book a bus ticket validate status like held,pending,confirmed and cancel the ticket")
    void Booking()
    {
        String id=given()
                .when()
                .spec(booking)
                .body(busdetails)
                .header("Authorization", "Bearer " + Token)
                .when()
                .post("")
                .then()
                        .body("state",equalToIgnoringCase("held"))
                        .body("amountPaise",greaterThan(0))
                        .extract().path("id");


        given()
                .spec(booking)
                .log().all()
                .header("Authorization", "Bearer " + Token)
                .pathParam("id",id)
                .log().all()
                .when()
                .post("/{id}/pay")
                .then()
                .statusCode(200)
                .log().all()
                .body("state", equalTo("PAYMENT_PENDING"));


        given()
                .spec(booking)
                .header("Authorization", "Bearer " + Token)
                .pathParam("id", id)
                .when()
                .post("/{id}/confirm")
                .then()
                .statusCode(200)
                .body("state", equalTo("CONFIRMED"))
                .body("pnr", notNullValue());

        given()
                .spec(booking)
                .header("Authorization", "Bearer " + Token)
                .pathParam("id", id)
                .when()
                .post("/{id}/cancel")
                .then()
                .statusCode(200)
                .body("state", equalTo("REFUNDED"));



    }



}
