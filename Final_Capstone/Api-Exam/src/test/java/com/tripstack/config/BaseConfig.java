package com.tripstack.config;

import com.tripstack.utils.ConfigReader;
import io.restassured.RestAssured;

public class BaseConfig {

    public static void setup() {

        RestAssured.baseURI = ConfigReader.get("base.url");
        RestAssured.basePath = "/api";

    }
}