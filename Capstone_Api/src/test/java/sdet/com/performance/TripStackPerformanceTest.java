package sdet.com.performance;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import sdet.com.services.TripStackApiService;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static sdet.com.support.TripStackConfig.BASE_URL;

public class TripStackPerformanceTest {

    @Test
    void searchBusShouldRespondWithin2Seconds() {

        String token = new TripStackApiService().login(
                BASE_URL,
                "peggy@tripstack.test",
                "Password@123"
        );

        long time =
                given()
                        .baseUri(BASE_URL)
                        .header("Authorization","Bearer "+token)
                        .queryParam("from","DEL")
                        .queryParam("to","IXC")
                        .queryParam("date","2026-07-30")
                        .when()
                        .get("/api/buses")
                        .time();

        System.out.println("Response Time = " + time);

        assertThat(time).isLessThan(2000);
    }

    @Test
    void loginShouldCompleteWithin1500ms() {

        long time =
                given()
                        .baseUri(BASE_URL)
                        .contentType(ContentType.JSON)
                        .body(Map.of(
                                "email","peggy@tripstack.test",
                                "password","Password@123"
                        ))
                        .when()
                        .post("/api/auth/login")
                        .time();

        System.out.println(time);

        assertThat(time).isLessThan(1500);
    }
    @Test
    void bookingShouldCompleteWithin3Seconds() {

        TripStackApiService api = new TripStackApiService();

        String token = api.login(BASE_URL,
                "peggy@tripstack.test",
                "Password@123");

        long time =
                given()
                        .baseUri(BASE_URL)
                        .header("Authorization","Bearer "+token)
                        .contentType(ContentType.JSON)
                        .body(Map.of(
                                "journeyType","bus",
                                "inventoryId","BUS-DELIXC-01",
                                "seatIds", List.of("L3"),
                                "refundable",true,
                                "holdTtlSec",120
                        ))
                        .when()
                        .post("/api/bookings")
                        .time();

        System.out.println(time);

        assertThat(time).isLessThan(3000);
    }
}
