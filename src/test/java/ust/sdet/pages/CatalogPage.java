package ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ust.sdet.support.Config;

public class CatalogPage extends BasePage{

    private static final By SEARCH = By.cssSelector(
            "[data-test='search-input']");

    private static final By Producthead = By.cssSelector(
            ".main-nav a:nth-of-type(2)");

    private static final By RESULT_COUNT = By.cssSelector(
            "[data-test='catalog-result-count']");

    private static final By FIRST_PRODUCT_LINK = By.cssSelector(
                    "[data-test='product-card'] a");

    public CatalogPage(WebDriver driver){
        super(driver);
    }
    public CatalogPage open(){
        openCatalogPage();
        visible(SEARCH);
        visible(RESULT_COUNT);
        return this;
    }
    public CatalogPage openCatalogPageClick(){

        header().opencart();


        return this;
    }

    public CatalogPage searchForProduct(String query){

        String previousResultCount = text(RESULT_COUNT);

        type(SEARCH,query);

        spinner(RESULT_COUNT,previousResultCount);

        return this;

    }

    public ProductPage openFirstProduct(){
        click(FIRST_PRODUCT_LINK);
        return new ProductPage(driver);
    }
}
