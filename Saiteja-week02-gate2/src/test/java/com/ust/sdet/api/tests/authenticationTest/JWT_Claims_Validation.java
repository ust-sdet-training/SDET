package com.ust.sdet.api.tests.authenticationTest;
import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWT_Claims_Validation {

    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    void verifyOpsJwtClaims() {

        String token = SpecFactory.getOAuthToken();
        String header = new String(
                Base64.getUrlDecoder().decode(token.split("\\.")[0]),
                StandardCharsets.UTF_8
        );
        String payload = new String(
                Base64.getUrlDecoder().decode(token.split("\\.")[1]),
                StandardCharsets.UTF_8
        );
        System.out.println("HEADER:");
        System.out.println(header);

        System.out.println();

        System.out.println("PAYLOAD:");
        System.out.println(payload);
        assertTrue(payload.contains("\"role\":\"OPS\""));
        assertTrue(payload.contains("orders:write"));
        assertTrue(payload.contains("orders:read"));
    }
}