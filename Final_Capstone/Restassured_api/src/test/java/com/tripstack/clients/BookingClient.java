package com.tripstack.clients;


import static io.restassured.RestAssured.*;


import com.tripstack.config.Config;
import com.tripstack.utils.TokenManager;



public class BookingClient {



    public String createBooking(String busId){


        return given()

                .baseUri(Config.get("base.url"))

                .header(
                        "Authorization",
                        "Bearer "+TokenManager.getToken()
                )

                .contentType("application/json")

                .body("""
{
"journeyType":"bus",
"inventoryId":"%s",
"seatIds":["L3"]
}
""".formatted(busId))


                .when()

                .post("/api/bookings")

                .then()

                .statusCode(201)

                .extract()

                .path("id");


    }



    public void paymentDecline(String id){


        given()

                .baseUri(Config.get("base.url"))

                .header(
                        "Authorization",
                        "Bearer "+TokenManager.getToken()
                )

                .body("{}")


                .when()

                .post("/api/bookings/"+id+"/pay")


                .then()

                .statusCode(402);


    }




    public String confirm(String id){


        return given()

                .baseUri(Config.get("base.url"))

                .header(
                        "Authorization",
                        "Bearer "+TokenManager.getToken()
                )


                .when()

                .post("/api/bookings/"+id+"/confirm")


                .then()

                .statusCode(200)

                .extract()

                .path("pnr");


    }


}