package com.shopkart.stepdefs;

import com.shopkart.data.secrets.Secrets;
import com.shopkart.support.BaseUITest;
import com.shopkart.ui.pages.*;
import io.qameta.allure.Step;

import static org.junit.jupiter.api.Assertions.*;

public class UIEndToEndTest extends BaseUITest {

    @Step
    public void makeAEndToEndJourney() {


        LoginPage loginPage = new LoginPage();
        CatalogPage catalogPage =loginPage.goToLoginPage().
                makeALogin(Secrets.get("alice.email"),Secrets.get("alice.password"));

        catalogPage.searchTheProduct(Secrets.get("product"));

        ProductPage productPage = catalogPage.goToProductPage();

        CartPage cartPage = productPage.addToCartWithLogin();

        CheckoutPage checkoutPage = cartPage.makeACheckout().placeTheOrder();


        assertAll(
                ()->assertEquals("placed",checkoutPage.orderStatus().toLowerCase())
        );
    }
}
