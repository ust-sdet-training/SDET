package week3.gate3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import week3.gate3.pages.components.ProductCard;
import week3.gate3.support.Config;


public class CatalogPage extends BasePage{
    public CatalogPage(WebDriver driver){
        super(driver);
    }

    private static final By SEARCH_INPUT = By.cssSelector("[data-test='search-input']");
    private static final By CARDS = By.cssSelector("[data-test='product-card']");
    private static final By PRODUCT_LINK = By.cssSelector("[data-test='product-card'] a");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");

    public CatalogPage open(){
        driver.get(Config.catalogUrl());
        visible(SEARCH_INPUT);
        visible(RESULT_COUNT);
        return this;
    }

    public CatalogPage search(String query){
        WebElement search = visible(SEARCH_INPUT);
        search.clear();
        search.sendKeys(query);
        search.sendKeys(Keys.ENTER);
        return this;
    }

    public ProductPage openFirstProduct(){
        visibleElements(PRODUCT_LINK).getFirst().click();
        return new ProductPage(driver);
    }

    public String firstCardTitle(){
        return new ProductCard(visibleElements(CARDS).getFirst()).title();
    }

}
