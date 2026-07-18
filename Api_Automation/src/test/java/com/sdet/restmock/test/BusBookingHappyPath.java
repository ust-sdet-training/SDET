package com.sdet.restmock.test;
import com.sdet.restmock.support.ProductFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static com.sdet.restmock.config.UserData.*;
import static com.sdet.restmock.support.ProductFactory.*;

public class BusBookingHappyPath {

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
                .body("count",notNullValue())
                .body("from", equalToIgnoringCase("HYD"))
                .body("to",equalToIgnoringCase("CCU"))
                .extract().path("buses[0].id");



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


        Response res1=given()
                .spec(booking)
                .header("Authorization", "Bearer " + Token)
                .pathParam("id",id)
                .when()
                .post("/{id}/pay");
                if(res1.statusCode()==502)
                {
                        res1.then()
                                .body("error",equalToIgnoringCase("GATEWAY_UNAVAILABLE"))
                                .body("message",containsString("connection reset"));
                }
                else if (res1.statusCode()==200)
                {

                    res1.then()
                            .body("state", equalTo("PAYMENT_PENDING"));

                }



        Response res2 =given()
                .spec(booking)
                .header("Authorization", "Bearer " + Token)
                .pathParam("id", id)
                .when()
                .post("/{id}/confirm");
        if(res2.statusCode()==200) {
                res2.then()
                    .body("state", equalTo("CONFIRMED"));
        }
        else if (res2.statusCode()==402)
        {
            res2.then()
                    .body("error",equalToIgnoringCase("GATEWAY_DECLINE"))
                    .body("message",equalToIgnoringCase("no captured payment"));
        }

//if(res2.statusCode()==200) {
//    given()
//            .spec(booking)
//            .header("Authorization", "Bearer " + Token)
//            .log().all()
//            .pathParam("pnr",res2.body())
//            .when()
//            .get("/{pnr}")
//            .then()
//            .statusCode(200)
//            .body("pnr", notNullValue());
//}


        Response res3=given()
                .spec(booking)
                .header("Authorization", "Bearer " + Token)
                .pathParam("id", id)
                .when()
                .post("/{id}/cancel");
                if(res2.statusCode()==402)
                {
                    res3.then().statusCode(200)
                            .body("pnr",emptyOrNullString());
                }

    }



}
