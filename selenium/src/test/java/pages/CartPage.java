package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {
    private By cartItemNames = By.cssSelector("a.pro-name");
        private By cartItemRows = By.cssSelector("div.itmBx");
        private By confirmRemoveButton = By.cssSelector("a.btn.btn-tertiary, button.confirm-remove");
        private By totalPrice = By.cssSelector("div.cart-total-price span, #priceDetails + div span.f15");
        private By emptyCartMessage =
            By.xpath("//p[contains(normalize-space(),'There are no items in this cart') or contains(normalize-space(),'Your cart is empty')]");
        private By continueShoppingButton =
            By.xpath("//a[contains(translate(.,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'CONTINUE')]");
    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean productExists(String productName) {
        List<WebElement> items = driver.findElements(cartItemNames);
        return items.stream()
                .anyMatch(el -> el.getText().trim().equalsIgnoreCase(productName));
    }

    public void removeProductByName(String productName) {
        By removeButton = By.xpath("//div[contains(@class,'itmBx') and .//a[contains(normalize-space(), '" + productName + "')]]//a[contains(.,'Remove') or contains(.,'REMOVE') or contains(@class,'remove')]");
        click(removeButton);
        // wait for confirmation popup visible
        waitForVisibility(confirmRemoveButton, 10);
    }

    public void confirmRemovePopup() {
        click(confirmRemoveButton, 10);
        // wait until the confirm popup disappears
        waitForInvisibility(confirmRemoveButton, 10);
    }

    public double getTotalPrice() {
        String text = getText(totalPrice);
        return parsePrice(text);
    }

    public int getRemainingItemCount() {
        return driver.findElements(cartItemRows).size();
    }

    public boolean isCartEmpty() {
        return !driver.findElements(emptyCartMessage).isEmpty();
    }

    public boolean isContinueShoppingButtonVisible() {
        return !driver.findElements(continueShoppingButton).isEmpty();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    private double parsePrice(String text) {
        String cleaned = text.replaceAll("[^0-9.]", "");
        if (cleaned.isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(cleaned);
    }
}
