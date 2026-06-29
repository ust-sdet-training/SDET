package com.ust.sdet.accountWorks.pages;

import com.ust.sdet.accountWorks.pages.components.ProductViewCard;
import com.ust.sdet.accountWorks.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductListingPage extends BasePage{

    private static final By PRODUCT_CARDS = By.cssSelector(".sg-col-inner");
    private static final By FIRST_PRODUCT = By.cssSelector(".sg-col-inner a");
    private static final By PRODUCT_TITLE = By.cssSelector(".sg-col-inner h2");
    private static final By PRODUCT_PRICE = By.cssSelector(".a-price-whole");
    private static final By PRODUCT_RATING = By.cssSelector(".a-row a-size-small");

    public ProductListingPage(WebDriver driver){
        super(driver);
    }

    public ProductListingPage areProductsAvailable(){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_CARDS));
        return this;
    }

    public List<ProductViewCard> cards(){
        return visibleElements(PRODUCT_CARDS).stream().map(ProductViewCard::new).toList();
    }

    public List<String> productTitles(){
        return cards().stream().map(ProductViewCard::title).toList();
    }

    public ProductListingPage scrollToTheProduct(){
        WebElement prod = driver.findElement(FIRST_PRODUCT);
        scrollIntoView(prod);
        return this;
    }
    public String isProductTitleSame(){
        visible(PRODUCT_TITLE);
        return text(PRODUCT_TITLE);
    }

    public ProductListingPage productTitleIsVisible(){
        visible(PRODUCT_TITLE);
        return this;
    }

    public ProductListingPage productPriceIsVisible(){
        visible(PRODUCT_PRICE);
        return this;
    }

    public ProductListingPage productRatingIsVisible(){
        visible(PRODUCT_RATING);
        return this;
    }

    public ActualProductPage openFirstProduct(){
        click(FIRST_PRODUCT);
        return new ActualProductPage(driver);
    }
}
