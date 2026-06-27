package com.week4.consumer;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductUITest {

    @BeforeAll
    static void setup() {
        Configuration.browser = "chrome";
    }

    @Test
    void verifyAvailabilityBadge() {

        File file = new File("src/test/resources/product.html");
        open(file.toURI().toString());

        ProductPage page = new ProductPage();

        assertEquals("IN STOCK", page.getAvailabilityText());
    }
}