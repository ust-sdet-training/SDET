package com.example.Selenide.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ProductCard {



   private final SelenideElement root;

    public ProductCard(SelenideElement root){
        this.root = root;

    }

    public String titles(){
        return root.$("[data-test='product-title']").getText();
    }

    public int prices(){
        return Integer.parseInt(root.$("[data-test='product-price']").getText().replaceAll("[^0-9]",""));
    }

}
