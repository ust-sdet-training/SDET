package com.sdet.restmock.test;

import com.sdet.restmock.support.ProductFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sdet.restmock.config.UserData.tamperSeat;
import static com.sdet.restmock.support.ProductFactory.booking;
import static com.sdet.restmock.support.ProductFactory.tampered;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;

public class SecurityNegativeTest {
    public static  String  TamperedToken;

    @Test
    @DisplayName("Tampered jwt token")
    void tamperToken()
    {
        TamperedToken= ProductFactory.getAuthToken()+"tampered"; //here i am creating the tampered token
        given()
                .when().log().all()
                .spec(tampered)
                .header("Authorization", "Bearer " + TamperedToken)
                .when()
                .get("")
                .then()
                .statusCode(401)
                .body("error",equalToIgnoringCase("unauthorized"));

    }


}
