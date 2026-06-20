package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CatalogPage;

public class CartBadge extends CatalogPage {

    private static final By CART_COUNT = By.cssSelector("[data-test='cart-count']");

    public CartBadge(WebDriver driver) {
        super(driver);
    }


    public int count() {

        return countBy(CART_COUNT);

    }

    public void expectCount(int expectedCount) {
        countExpected(CART_COUNT,String.valueOf(expectedCount));
    }

}
