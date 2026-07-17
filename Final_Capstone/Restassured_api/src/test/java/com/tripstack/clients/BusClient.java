package com.tripstack.clients;


import static io.restassured.RestAssured.*;

import com.tripstack.config.Config;


public class BusClient {



    public String searchBus(){


        return given()

                .baseUri(Config.get("base.url"))

                .queryParam("from","HYD")

                .queryParam("to","DEL")


                .when()

                .get("/api/buses")


                .then()

                .statusCode(200)

                .extract()

                .path("buses[0].id");


    }



    public void seatMap(String id){


        given()

                .baseUri(Config.get("base.url"))

                .when()

                .get("/api/buses/"+id+"/seats")

                .then()

                .statusCode(200);


    }



}