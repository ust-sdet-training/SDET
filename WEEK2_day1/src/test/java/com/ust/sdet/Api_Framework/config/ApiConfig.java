package com.ust.sdet.Api_Framework.config;

import com.ust.sdet.dbframework.support.TestEnvironment;


public class ApiConfig {

    private ApiConfig(){}

    public static final String BASE_URL =
            TestEnvironment.optional(
                    "baseUrl",
                    "http://localhost:4000"
            );

}