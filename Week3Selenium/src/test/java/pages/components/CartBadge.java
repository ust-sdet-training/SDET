package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartBadge {

    private static final By COUNT =
            By.cssSelector("[data-test='cart-count']");

    private final WebDriverWait wait;

    public CartBadge(WebDriverWait wait) {
        this.wait = wait;
    }

    public int count() {

        try {
            return Integer.parseInt(
                    wait.until(
                                    ExpectedConditions.visibilityOfElementLocated(COUNT))
                            .getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void expectCount(int expectedCount) {

        if (expectedCount == 0) {
            return;
        }

        wait.until(
                ExpectedConditions.textToBe(
                        COUNT,
                        String.valueOf(expectedCount)));
    }
}