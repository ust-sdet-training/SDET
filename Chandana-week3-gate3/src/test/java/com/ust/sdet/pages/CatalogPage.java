package com.ust.sdet.pages;

import com.ust.sdet.pages.components.Header;
import com.ust.sdet.pages.components.ProductCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CatalogPage extends BasePage {

    private static final By SEARCH_INPUT =
            By.cssSelector("[data-test='search-input']");

    private static final By PRODUCT_CARDS =
            By.cssSelector("[data-test='product-card']");

    private final Header header;

    public CatalogPage(WebDriver driver) {
        super(driver);

        this.header = new Header(driver);

        visible(SEARCH_INPUT);
    }

    public CatalogPage search(String product) {
        type(SEARCH_INPUT, product);
        return this;
    }

    public List<ProductCard> products() {
        return visibleElements(PRODUCT_CARDS)
                .stream()
                .map(ProductCard::new)
                .toList();
    }

    public ProductCard firstProduct() {
        return products().get(0);
    }

    public ProductPage openFirstMatchingProduct() {

        firstProduct().open();
        System.out.println(
                "Current URL = " + driver.getCurrentUrl()
        );

        return new ProductPage(driver);
    }

    public Header header() {
        return header;
    }
}