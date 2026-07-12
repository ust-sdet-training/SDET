package com.shopkart.data.tests;

import com.shopkart.api.ProductClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class productClientStandardTest{
    @Test
    void structureTest() {

        Method[] methods = ProductClient.class.getDeclaredMethods();

        assertTrue(
                Arrays.stream(methods)
                        .filter(m -> !m.getName().startsWith("get"))
                        .anyMatch(m ->
                                m.getReturnType().equals(Response.class)
                        )
        );
    }
}