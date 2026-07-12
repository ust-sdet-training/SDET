package com.shopkart.data.tests;

import com.shopkart.ui.pages.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class FrameworkStandardTest {

    @ParameterizedTest
    @ValueSource(classes = {
            LoginPage.class,
            CatalogPage.class,
            ProductPage.class,
            CartPage.class,
            CheckoutPage.class
    })
    void pageObjectsFollowStandards(Class<?> pageClass) {

        List<Field> locators =
                Arrays.stream(pageClass.getDeclaredFields())
                        .filter(f -> f.getType().equals(By.class))
                        .toList();

        assertTrue(
                locators.stream()
                        .allMatch(f -> Modifier.isPrivate(f.getModifiers()))
        );
    }
}

