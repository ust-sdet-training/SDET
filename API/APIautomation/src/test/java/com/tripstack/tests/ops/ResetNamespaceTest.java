package com.tripstack.tests.ops;

import com.tripstack.base.BaseTest;
import com.tripstack.utils.TokenManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class ResetNamespaceTest extends BaseTest {

    @Test
    public void verifyNamespaceReset() {

        String token =
                TokenManager.getToken();

        Response response =
                opsClient.resetNamespace(token);

        System.out.println(
                "STATUS CODE = "
                        + response.statusCode()
        );

        System.out.println(
                "RESPONSE BODY = "
        );

        response.prettyPrint();
    }
}