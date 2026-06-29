package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BasePage {
    private By searchBox = By.cssSelector("input[type='search'][placeholder='What are you looking for?']");
    private By searchBox2 = By.cssSelector(".head-search2 input");
    private By productCard = By.cssSelector("div[id^='lp-itm-']"); 
    private By productName = By.cssSelector("a.pro-name"); 
    private By addButton = By.xpath("//a[contains(@class,'border-primary') and normalize-space()='Add to Cart']");
    private By goToCartButton =
    By.xpath("//a[contains(@class,'border-primary') and normalize-space()='Go to Cart']");
    private By cartIcon = By.cssSelector("a[href='/cart']");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void openHomePage(String url) {
        driver.get(url);
    }

    public void searchProduct(String query) {
        WebElement box = waitForVisibility(searchBox);
        box.click();

        WebElement box2 = waitForVisibility(searchBox2);
        box2.clear();
        box2.sendKeys(query);

        // wait until the expanded input reflects the typed query
        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        localWait.until(ExpectedConditions.attributeToBe(searchBox2, "value", query));

        // submit via ENTER (more reliable after second box receives text)
        box2.sendKeys(Keys.ENTER);

        // wait for results to load
        WebDriverWait resultsWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        resultsWait.until(ExpectedConditions.visibilityOfElementLocated(productCard));
    }

    public List<String> collectFirstTwoProductNames() {
        // Wait until product cards are present and visible
        waitForVisibility(productCard);
        List<WebElement> cards = driver.findElements(productCard);
        List<String> names = new ArrayList<>();

        for (WebElement card : cards) {
            try {
                WebElement nameEl = card.findElement(productName);
                String name = nameEl.getText().trim();
                if (!name.isEmpty()) {
                    names.add(name);
                }
            } catch (Exception ignored) {
            }
            if (names.size() >= 2) {
                break;
            }
        }
        return names;
    }

    public void addFirstTwoProducts() {
        List<String> names = collectFirstTwoProductNames();
        int toAdd = Math.min(2, names.size());
        for (int i = 0; i < toAdd; i++) {
            addProductByName(names.get(i));
        }
    }

    public void addProductByIndex(int index) {
        // Re-fetch the cards each time to avoid stale elements
        waitForVisibility(productCard);
        List<WebElement> cards = driver.findElements(productCard);
        if (index < 0 || index >= cards.size()) {
            return;
        }
        WebElement card = cards.get(index);
        try {
            WebElement btn = card.findElement(addButton);
            scrollIntoViewElement(btn);
            waitUntilClickable(btn);
            try {
                btn.click();
            } catch (Exception e) {
                jsClick(btn);
            }
        } catch (Exception ignored) {
        }
    }

    public void openCart() {
        click(cartIcon);
    }

    public boolean isGoToCartVisibleForProduct(String productNameText) {
        waitForVisibility(productCard);
        List<WebElement> cards = driver.findElements(productCard);
        for (WebElement card : cards) {
            try {
                WebElement nameEl = card.findElement(productName);
                String name = nameEl.getText().trim();
                if (name.equalsIgnoreCase(productNameText)) {
                    List<WebElement> gotoBtns = card.findElements(goToCartButton);
                    return !gotoBtns.isEmpty() && gotoBtns.get(0).isDisplayed();
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    // --- helper methods kept private to keep public API small ---
    private void addProductByName(String name) {
        waitForVisibility(productCard);
        List<WebElement> cards = driver.findElements(productCard);
        for (WebElement card : cards) {
            try {
                WebElement nameEl = card.findElement(productName);
                String text = nameEl.getText().trim();
                if (text.equalsIgnoreCase(name)) {
                    WebElement btn = card.findElement(addButton);
                    scrollIntoViewElement(btn);
                    waitUntilClickable(btn);
                    try {
                        btn.click();
                    } catch (Exception e) {
                        jsClick(btn);
                    }

                    // after adding, wait until either Go to Cart appears for that card
                    WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    localWait.until(driver1 -> !card.findElements(addButton).isEmpty() || !card.findElements(goToCartButton).isEmpty());
                    return;
                }
            } catch (Exception ignored) {
            }
        }
    }

    

    private void waitUntilClickable(WebElement el) {
        try {
            WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            localWait.until(ExpectedConditions.elementToBeClickable(el));
        } catch (Exception ignored) {
        }
    }

    private void jsClick(WebElement el) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        } catch (Exception ignored) {
        }
    }
}
