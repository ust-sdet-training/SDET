package Purplle_Test.Tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductListTest extends UI_Test.BasePage {

    private static final By SEARCH_INPUT = By.cssSelector("input.form-control");
    private static final By FIRST_PRODUCT = By.id("lp-itm-0");
    private static final By SECOND_PRODUCT = By.id("lp-itm-1");
    private static final By THIRD_PRODUCT = By.id("lp-itm-2");
    private static final By PRODUCT_TITLE = By.cssSelector(".product-title.fs-7.text-start.text-black.fw-normal");
    private static final By PAGE_TITLE = By.id("titleHeading");
    private static final By ADD_TO_CART = By.cssSelector("a.btn.rounded.bg-white.border.border-primary.text-primary.text-center.d-block.fs-7.fw-bolder");
    private static final By CART_COUNT = By.cssSelector(".cart-count-text.ng-star-inserted");

    List<String> productTitles = new ArrayList<>();

    @Test
    @DisplayName("Product list iteration")
    void getProductList() {

        input(SEARCH_INPUT, "lipstick");

        webDriver.get("https://www.purplle.com/search?q=lipstick");

        String pageTitle = text(PAGE_TITLE);

        assertEquals("Lipstick", pageTitle);

        WebElement firstProduct = visible(FIRST_PRODUCT);
        String firstTitle = text(firstProduct.findElement(PRODUCT_TITLE));
        productTitles.add(firstTitle);

        WebElement secondProduct = visible(SECOND_PRODUCT);
        String secondTitle = text(secondProduct.findElement(PRODUCT_TITLE));
        productTitles.add(secondTitle);

        WebElement thirdProduct = visible(THIRD_PRODUCT);
        String thirdTitle = text(thirdProduct.findElement(PRODUCT_TITLE));
        productTitles.add(thirdTitle);

        for (String title : productTitles) {
            System.out.println("Product Title: " + title);
        }

        click(ADD_TO_CART);

        String cartCount = text(CART_COUNT);

        assertEquals("1", cartCount);
    }
}