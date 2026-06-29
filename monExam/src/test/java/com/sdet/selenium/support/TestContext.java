package com.sdet.selenium.support;

import com.sdet.selenium.pages.AmazonHomePage;
import com.sdet.selenium.pages.ProductPage;
import com.sdet.selenium.pages.SearchResultsPage;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class TestContext {
    private WebDriver driver;
    private AmazonHomePage amazonHomePage;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;
    private String searchKeyword;
    private List<String> productNames;
    private String selectedProductName;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public AmazonHomePage getAmazonHomePage() {
        return amazonHomePage;
    }

    public void setAmazonHomePage(AmazonHomePage amazonHomePage) {
        this.amazonHomePage = amazonHomePage;
    }

    public SearchResultsPage getSearchResultsPage() {
        return searchResultsPage;
    }

    public void setSearchResultsPage(SearchResultsPage searchResultsPage) {
        this.searchResultsPage = searchResultsPage;
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public void setProductPage(ProductPage productPage) {
        this.productPage = productPage;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    public String getSelectedProductName() {
        return selectedProductName;
    }

    public void setSelectedProductName(String selectedProductName) {
        this.selectedProductName = selectedProductName;
    }
}
