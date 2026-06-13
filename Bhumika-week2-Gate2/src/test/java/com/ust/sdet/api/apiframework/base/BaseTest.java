package com.ust.sdet.api.apiframework.base;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import org.junit.jupiter.api.BeforeAll;

import com.ust.sdet.api.apiframework.config.ConfigManager;
public class BaseTest {

        @BeforeAll
        static void setup() {

            RestAssured.baseURI = ConfigManager.BASE_URL;

            RestAssured.config = RestAssured.config()
                    .encoderConfig(
                            EncoderConfig.encoderConfig()
                                    .defaultContentCharset("UTF-8")
                    );
        }
    }


