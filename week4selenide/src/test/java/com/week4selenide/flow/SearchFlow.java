package com.week4selenide.flow;

import com.week4selenide.pages.*;

public class SearchFlow{

    private CatalogPage catalogPage;
    private ProductPage productPage;

    public SearchFlow() {
        catalogPage = new CatalogPage();
        productPage = new ProductPage();
    }

    public CatalogPage openCatalogPage(){
        catalogPage.openPage();
        return catalogPage;
    }

    public CatalogPage Search(String query){
        catalogPage.searchProduct(query).verifyResult(query);
        return catalogPage;
    }

    public ProductPage clickOnFirstProduct(){
       productPage = catalogPage.openFirstProduct();
        return productPage;
    }

    public void verifyAvalibilityBadgeisVisible(){
        productPage.abilityBadgeVisible();
    }
}
