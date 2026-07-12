package com.shopkart.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.shopkart.Config.AppConfig;
import com.shopkart.ui.locators.Xp;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CatalogPage extends Xp {

    public CatalogPage goToCatalog(){
      open(AppConfig.homeUrl());
      return this;
    }


    public CatalogPage searchTheProduct(String product){
        product(product).setValue(product).pressEnter();
       return this;
    }

    public boolean VerifyTheProduct(String product){
        productList()
                .filter(visible)
                .findBy(text(product))
                .shouldBe(visible);

        return true;
    }

    public ProductPage goToProductPage(){

        theProduct().click();
        return new ProductPage();
    }
}
