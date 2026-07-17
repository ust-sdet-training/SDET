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
    @DisplayName("get the bus details")
    void getTheBusDetails()
    {
        given()
                .when()
                .spec(getbus).log().all()
                .queryParam("from", "HYD")
                .queryParam("to", "CCU")
                .queryParam("date", "2026-07-27")
                .get("")
                .then()
                .log().all()
                .statusCode(200)
                .log().all()
                .body("count",notNullValue())
                .body("from", equalToIgnoringCase("HYD"))
                .body("to",equalToIgnoringCase("CCU"));
    }

    @Test
    @DisplayName("book a ticket in the bus")
    void getaTicketinBus()
    {
        given()
                .when()
                .spec(booktic)
                .body(busdetails)
                .header("Authorization", "Bearer " + Token)
                .log().all()
                .post("")
                .then().log().all()
                .body("amountPaise",greaterThan(0))
                .body("id",notNullValue())
                .statusCode(201);

    }

    @Test
    @DisplayName("check status of  the ticket")
    void checkTicket()
    {
        given()
                .when()
                .spec(pay)
                .header("Authorization", "Bearer " + Token).log().all()
                .pathParam("id",getid())
                .post("/api/bookings/{id}/pay")
                .then().
                body("state",equalToIgnoringCase("PAYMENT_PENDING"));

    }

    @Test
    @DisplayName("check status of  the ticket")
    void confirmTicket()
    {
        given()
                .when()
                .spec(pay)
                .header("Authorization", "Bearer " + Token).log().all()
                .pathParam("id",getid())
                .post("/api/bookings/{id}/confirm")
                .then().log().all()
                .body("state",equalToIgnoringCase("CONFIRMED"));

    }




//    @Test
//    @DisplayName("404 response by fetching invalid order")
//    void getInvlalidCode()
//    {
//        given()
//                .when()
//                .spec(get)
//                .log().all()
//                .get("")
//                .then()
//                .log().all()
//                .statusCode(404);
//    }
//    @Test
//    @DisplayName("delete a booking and validating status code by fetching the same deleted order")
//    void deleteIDandValidate()
//    {
//        given()
//                .when()
//                .spec(delete)
//                .log().all()
//                .delete("")
//                .then()
//                .log().all();
//        given()
//                .when()
//                .spec(getdel)
//                .log().all()
//                .get(" ")
//                .then().
//                log().all().statusCode(404);
//
//    }
//    @Test @DisplayName("Getting the booking without delete the order")
    void GetwithoutDelete(){
        given()
                .when()
                .spec(getdel)
                .log().all()
                .get(" ")
                .then().
                log().all().statusCode(404);
    }




}
