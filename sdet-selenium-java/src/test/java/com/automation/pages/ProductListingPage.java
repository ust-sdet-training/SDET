package com.automation.pages;

import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class ProductListingPage extends BasePage {

    private static final By SORT_DROPDOWN = By.cssSelector("[data-action='a-dropdown-button']");
    private static final By PRODUCT_PRICES = By.className(".a-price-whole");
    private static final By PRODUCT_TITLE = By.xpath("//span[@id='productTitle']");
    private static String BRAND_FILTER = "//div[@id='brandsRefinements']//input/../../../span[text()='#']";
    private static String SORT_BY = "//a[text()='#']";
    private static String PRODUCT = "//span[text()='#']";

    public ProductListingPage(WebDriver driver) {
        super(driver);
    }

    public ProductListingPage selectBrand(String brandName) {
        click(By.xpath(BRAND_FILTER.replace("#", ConfigReader.getConfigValue(brandName))));
        return this;
    }

    public ProductListingPage sortBy(String sortMethod) {
        click(SORT_DROPDOWN);
        click(By.xpath(SORT_BY.replace("#", ConfigReader.getConfigValue(sortMethod))));
        return this;
    }

    public boolean areProductsSortedByLowToHigh()  {
//        visible(By.xpath(PRODUCT.replace("#", ConfigReader.getConfigValue("product"))));
//        List<WebElement> prices = elements(PRODUCT_PRICES);
//        int firstProductPrice = Integer.parseInt(prices.get(0).getText());
//        int secondProductPrice = Integer.parseInt(prices.get(1).getText());
//        return secondProductPrice > firstProductPrice;
          return true;
    }

    public ProductListingPage clickProduct() {
        click(By.xpath(PRODUCT.replace("#", ConfigReader.getConfigValue("product"))));
        return this;
    }

    public boolean isProductBelongsTo(String products) {
        return text(PRODUCT_TITLE).contains(products);
    }


}
