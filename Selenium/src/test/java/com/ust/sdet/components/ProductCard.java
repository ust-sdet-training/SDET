package com.ust.sdet.components;

import com.ust.sdet.pages.BasePage;
import com.ust.sdet.pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductCard extends BasePage {
    private final String productName;
    private final By root;

    public ProductCard(WebDriver driver, String productName) {
        super(driver);
        this.productName = productName;
        this.root = By.xpath("//*[@data-test='product-card'][.//*[@data-test='product-title' and normalize-space()='"
                + productName + "']]");
        visible(root);
    }

    public String title() {
        return visible(root).findElement(By.cssSelector("[data-test='product-title']")).getText();
    }

    public String price() {
        return visible(root).findElement(By.cssSelector("[data-test='product-price']")).getText();
    }

    public ProductPage open() {
        visible(root).findElement(By.linkText("View " + productName)).click();
        return new ProductPage(driver);
    }
}
