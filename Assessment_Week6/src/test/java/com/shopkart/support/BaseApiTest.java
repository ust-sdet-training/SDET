package com.shopkart.support;

import com.shopkart.api.ProductClient;
import com.shopkart.stepdefs.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaseApiTest {



    @Test
    void apiHappyPath() throws Exception {
        ApiEndToEndTest api = new ApiEndToEndTest();
        api.makeAFullApiJourney();
    }

    @Test
    void  apiProductSearch(){
        ApiProductSearchTest api = new ApiProductSearchTest();
        api.full_product_search();
    }

    @Test
    void apiOrderAccess(){
        ApiOrderAccess api = new ApiOrderAccess();
        api.makeAFullApiJourney();

        api.unauthorizedAccess();
    }

    @Test
    void apiCancelTest()
    {
        ApiCancelOrderTest api = new ApiCancelOrderTest();
        api.checkTheCancelTest();
    }

    @Test
    void apiOutOfStockTest(){
        ApiOutOfStockTest api = new ApiOutOfStockTest();
        api.startOutOfStockTest();
    }

    @Test
    void cartTotalValidation() {

        ApiCartTotalTest api = new ApiCartTotalTest();

        api.validateCartTotalJourney();
    }

}
