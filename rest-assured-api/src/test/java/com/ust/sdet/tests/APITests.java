package com.ust.sdet.tests;

import com.ust.sdet.specs.RequestSpecs;
import com.ust.sdet.specs.ResponseSpecs;
import io.restassured.RestAssured;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class APITests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
    }

    @Test
    @DisplayName("create and get booking")
    void createAndGetBooking(){


    }
}
