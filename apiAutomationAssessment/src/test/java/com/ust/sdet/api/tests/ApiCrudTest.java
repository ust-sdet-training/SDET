package com.ust.sdet.api.tests;

import com.ust.sdet.api.config.ApiConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.support.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiCrudTest {

    static String token;
    static int createdPetId;
    static Map<String, Object> requestBody;

    @BeforeAll
    static void setup() {
        requestBody = Map.of(
                        "firstname" ,"Jim",
                "lastname" , "Brown",
                "totalprice" , 111,
                "depositpaid" , true,
                "bookingdates" , Map.of(
            "checkin" , "2018-01-01",
                    "checkout" , "2019-01-01"
                ),
        "additionalneeds" , "Breakfast"
        );
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Test execution completed ");
    }


    public  String testCreateToken() {

        var  requestBody = Map.of("username","admin","password","password123");
        var response = given()
                .spec(authedTokenCreatedRequest)
                .body(requestBody)
                .when()
                .post("/auth")
                .then()
                .spec(commonJsonResponse)
                .extract().response();

        JsonPath json = response.jsonPath();
        return  json.getString("token");

    }

    @Test
    @Order(1)
    @DisplayName("GET - get all booking")
    void testGetAllBooking() {

        var response = given()
                .spec(commonJsonRequest)
                .when()
                .get("/booking")
                .then()
                .spec(commonJsonResponse)
                .extract()
                .response();


        assertNotNull(response.jsonPath().getInt("[0].bookingid"));
    }

    @Test
    @Order(2)
    @DisplayName("Post - Create new booking")
    void createBooking() {

        var response = given()
                .spec(commonJsonRequest)
                .body(requestBody)
                .when()
                .post("/booking")
                .then()
                .spec(commonJsonResponse)
                .extract()
                .response();
        JsonPath json = response.jsonPath();
        createdPetId = json.getInt("bookingid");


        assertTrue(json.getInt("bookingid")>=0);
    }

    @Test
    @Order(3)
    @DisplayName("GET - Fetch booking By ID")
    void getBookingByID() {

        var response = given()
                .spec(commonJsonRequest)
                .pathParam("id",2)
                .when()
                .get("/booking/{id}")
                .then()
                .spec(commonJsonResponse)
                .extract()
                .response();

//        assertEquals(createdPetId,response.jsonPath().getInt("bookingid"));
       assertNotNull(response.jsonPath().getString("firstname"));
        assertTrue(response.jsonPath().getInt("totalprice")>=0);
    }

    @Test
    @Order(4)
    @DisplayName("GET - Fetch booking By ID")
    void getBookingByIDQueryPath() {

        var response = given()
                .spec(commonJsonRequest)
                .queryParam("id",2)
                .when()
                .get("/booking/")
                .then()
                .spec(commonJsonResponse)
                .extract()
                .response();

        assertNotNull(response.jsonPath().getString("firstname"));

    }



    @Test
    @Order(3)
    @DisplayName("PUT - update booking")
    void updateBookingById() {

        var response = given()
                .spec(commonJsonRequest)
                .header("Cookie","token="+testCreateToken())
                .pathParam("id",1)
                .body(requestBody)
                .when()
                .put("/booking/{id}")
                .then()
                .spec(commonJsonResponse)
                .extract()
                .response();


        assertEquals("Jim",response.jsonPath().getString("firstname"));
    }




    @Test
    @Order(5)
    @DisplayName("DELETE - Delete Booking")
    void testDeleteBooking() {

        var response = given()
                .spec(commonJsonRequest)
                .body(requestBody)
                .when()
                .post("/booking")
                .then()
                .spec(commonJsonResponse)
                .extract()
                .response();
        JsonPath json = response.jsonPath();
       int Id = json.getInt("bookingid");

       given()
                .spec(commonJsonRequest)
                .header("Cookie","token="+testCreateToken())
                .pathParam("id",Id)
                .when()
                .delete("/booking/{id}")
                .then()
                .statusCode(201)
                .extract()
                .response();


               var res =  given()
                        .spec(commonJsonRequest)
                        .when()
                        .get("/booking/{id}",Id)
                        .then()
                        .statusCode(404)   // 404
                        .extract().response();


        assertEquals(404, res.statusCode());
        assertThat(res.statusCode(), equalTo(404));
    }


}
