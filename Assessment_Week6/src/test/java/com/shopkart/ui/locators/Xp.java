package com.shopkart.ui.locators;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.Stack;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Xp {

    private static final  String SEARCH = "//input[@id='catalog-search']";
    private static final String SIGNIN = "//button[normalize-space()='Sign in']";
    private static final String PRODUCT_LIST = "//div[@class='product-card product']";
    private static final String PRODUCT = "//button[contains(@class,'product-image')]";
    private static final String PRIMARY = "//button[@class='primary-button']";
    private static final String EMAIL = "//input[@id ='email']";
    private static final String PASSWORD = "//input[@id ='password']";
    private static final String TEXTAREA = "//textarea";
    private static final  String ORDER_STATUS= "//dd[@data-field='order-status']";
    private static final  String ORDERTOTAL = "//dd[@data-field='order-total']";
    public static SelenideElement product(String name)   {
        return $x(String.format(SEARCH, name));
    }

    public static ElementsCollection productList(){
        return $$x(PRODUCT_LIST);
    }

    public static SelenideElement theProduct(){
        return $x(PRODUCT);
    }

    public static SelenideElement addToCart() {
        return $x(PRIMARY);
    }

    public static SelenideElement checkout(){
        return $x(PRIMARY);
    }
    public static SelenideElement email(){
        return $x(EMAIL);
    }

    public static  SelenideElement password(){
        return $x(PASSWORD);
    }

    public static SelenideElement goToLogin(){
        return $x(SIGNIN);
    }

    public static SelenideElement login(){
        return $x(PRIMARY);
    }

    public static SelenideElement enterAddress(){
        return $x(TEXTAREA);
    }

    public static SelenideElement placeOrder(){
        return $x(PRIMARY);
    }

    public static SelenideElement status(){
        return $x(ORDER_STATUS);
    }

    public static SelenideElement total(){
        return $x(ORDERTOTAL);
    }
}
