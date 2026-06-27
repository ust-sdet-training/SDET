package com.ust.sdet.pages.component;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartBadge {

    private static final By BADGE =
            By.cssSelector("[data-test='cart-count']");

    private final WebDriver driver;

    public CartBadge(WebDriver driver) {
        this.driver = driver;
    }

    public void expectCount(int expected) {
        int actual = Integer.parseInt(
                driver.findElement(BADGE).getText()
        );

        System.out.println(actual);

        assertEquals(expected, actual);
    }
}