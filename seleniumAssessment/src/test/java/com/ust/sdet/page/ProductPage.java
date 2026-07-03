package com.ust.sdet.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js     = (JavascriptExecutor) driver;
    }

    public By waitForProducts() {
        String[] selectors = {
                "div.product-title",
                "p.product-title",
                "[class*='product-title']",
                "[class*='productName']",
                "[class*='product-name']"
        };
        for (String sel : selectors) {
            try {
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(sel), 0));
                return By.cssSelector(sel);
            } catch (TimeoutException ignored) {}
        }
        return null;
    }

    public void filterByBrand(String brandName) throws InterruptedException {
        String brandDropdowns ="//*[@id=\"filterHeader\"]/div/div/ul/li[2]/a/span[1]";
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(brandDropdowns)));
                scrollTo(el);
                el.click();


        String[] brandXpaths = {
                "//label[contains(normalize-space(),'" + brandName + "')]",
                "//li[contains(normalize-space(),'"    + brandName + "')]",
                "//span[contains(normalize-space(),'"  + brandName + "')]",
                "//div[contains(normalize-space(),'"   + brandName + "')]"
        };
        for (String xp : brandXpaths) {
            try {
                WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xp)));
                scrollTo(ele);
                ele.click();
                break;
            } catch (TimeoutException ignored) {}
        }

        String brand ="//*[@id=\"filterHeader\"]/div/div/ul/li[2]/div/div[2]/ul/li[1]/label/div/span";
        WebElement mamaearth = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(brand)));
        scrollTo(mamaearth);
        mamaearth.click();

        String filter ="//*[@id=\"filterHeader\"]/div/div/ul/li[2]/div/div[1]/div[2]/div/a/span";
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(filter)));
        scrollTo(filterButton);
        filterButton.click();
    }

    public void filterByPrice() throws InterruptedException {
        String priceDropdowns = "//*[@id=\"filterHeader\"]/div/div/ul/li[3]/a/span[1]";
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(priceDropdowns)));
                scrollTo(el);
                el.click();


        String priceXpaths = "//*[@id=\"filter_2\"]/ul/li[3]/label/span/span";
                WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(priceXpaths)));
                scrollTo(ele);
                ele.click();

        String filter = "//*[@id='filter_2']/div[2]/a";
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(filter)));
        scrollTo(filterButton);
        js.executeScript("arguments[0].click();", filterButton);
    }

    public void sortByLowPrice() {
        String sortXpaths = "//*[contains(text(),'Sort By')]";
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(sortXpaths)));
        scrollTo(el);
        el.click();

        String lowPriceXpaths = "//*[normalize-space()='Low Price']";
                WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(lowPriceXpaths)));
                ele.click();

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div.product-title"), 0));
    }

    public List<String> getProductNames(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        List<String> names = new ArrayList<>();
        for (WebElement el : elements) {
            String text = el.getText().trim();
            if (!text.isEmpty()) names.add(text);
        }
        return names;
    }

    public int getProductCount(By locator) {
        return getProductNames(locator).size();
    }

    private void clickApplyFilter() {
        String applyXpaths =
                "//button[normalize-space()='Apply Filter']";
                List<WebElement> buttons = driver.findElements(By.xpath(applyXpaths));
                for (WebElement btn : buttons) {
                    if (btn.isDisplayed()) {
                        scrollTo(btn);
                        btn.click();
                        return;
                    }
        }
    }

    private void scrollTo(WebElement el) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }
}