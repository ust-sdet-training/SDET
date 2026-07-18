package com.ust.tripStack.tests;

import com.ust.tripStack.api.client.*;

import com.ust.tripStack.report.ExtentTestListener;
import com.ust.tripStack.support.TestEnvironment;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic("tripStack Journeys")
@Feature("Full Booking Journey")
@Owner("Shahbaz Ahmad")
@ExtendWith(ExtentTestListener.class)
public class PTest {
    AuthClient auth = new AuthClient();
    HomeClient home = new HomeClient();





    @Test
    @DisplayName("login start with credentials")
    void testPractice()
    {
        Response res =  auth.login(
                TestEnvironment.required("CUSTOMER_MAIL"),TestEnvironment.required("CUSTOMER_PASSWORD"));


        String token = res.jsonPath().getString("token");

        home.reset(token);

    }
}
