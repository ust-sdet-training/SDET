package com.week7.finalgate.API.Factory;

import com.week7.finalgate.API.config.ApiConfig;
import io.restassured.RestAssured;

public final class RestClient {

    private RestClient() {}

    public static void initialize() {

        RestAssured.baseURI = ApiConfig.BASE_URL;
        RestAssured.basePath = ApiConfig.BASE_PATH;

    }

}

