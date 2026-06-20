package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductCard {

    private final WebElement root;

    public ProductCard(WebElement root) {
        this.root = root;
    }

    public String title() {
        return root.findElement(
                By.cssSelector("[data-test='product-title']")
        ).getText();
    }

    public Integer price() {

        String priceText = root.findElement(
                By.cssSelector("[data-test='product-price']")
        ).getText();

        priceText = priceText.replaceAll("[^0-9]", "");

        return Integer.parseInt(priceText);
    }
}