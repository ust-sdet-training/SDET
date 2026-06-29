package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {

    WebDriver driver;
    WebDriverWait wait;

    public SearchResultPage(WebDriver driver) {

        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    }

    By goboultBrand = By.xpath("//span[text()='GOBOULT']");
    By sortDropdown = By.id("s-result-sort-select");
    By searchResults = By.cssSelector("div[data-component-type='s-search-result']");
    By prices = By.xpath("//span[@class='a-price']//span[@class='a-price-whole']");
    By firstProduct = By.cssSelector("h2 a");

    public void selectBrand() {

        WebElement brand =
                wait.until(ExpectedConditions.visibilityOfElementLocated(goboultBrand));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", brand);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", brand);

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchResults, 5));

    }

    public void sortLowToHigh() {

        WebElement dropdown =
                wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));

        Select select = new Select(dropdown);

        select.selectByVisibleText("Price: Low to High");

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchResults, 5));

    }

    public List<Integer> getPrices() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(prices));

        List<WebElement> priceList = driver.findElements(prices);

        List<Integer> values = new ArrayList<>();

        for (WebElement p : priceList) {

            String text = p.getText().replace(",", "").trim();

            if (!text.isEmpty()) {

                try {

                    values.add(Integer.parseInt(text));

                } catch (Exception e) {

                }

            }

        }

        return values;

    }
}