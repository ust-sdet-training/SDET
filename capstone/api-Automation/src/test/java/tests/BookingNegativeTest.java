package tests;


import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import utils.ConfigReader;
import utils.TokenManager;
import specs.RequestSpec;

public class BookingNegativeTest {
    @Test
    void shouldRejectBookingWithoutInventory(){
        String token = TokenManager.getToken();
        Response response =
                given()
                        .spec(RequestSpec.request())
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
                        .body(
                                """
                                {
                                  "journeyType":"flight",
                                  "inventoryId":"",
                                  "seatIds":["15A"],
                                  "refundable":true,
                                  "holdTtlSec":120
                                }
                                """
                        )
                        .when()
                        .post(ConfigReader.get("booking.endpoint"))
                        .then()
                        .log().all()
                        .extract()
                        .response();
        assertEquals(400, response.statusCode());
    }
}