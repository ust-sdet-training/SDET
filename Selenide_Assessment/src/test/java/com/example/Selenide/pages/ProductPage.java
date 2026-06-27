package com.example.Selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.commands.ShouldHave;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductPage {

    SelenideElement addtoCart = $("[data-test ='add-to-cart']");
    SelenideElement producttitle = $("[data-test = 'detail-name']");
    ElementsCollection product_meta = $$(".product-meta");
    public CartPage add_to_the_cart(){
        producttitle.shouldBe(visible);
        addtoCart.click();
        return new CartPage();
    }

   public void checkvisibility(){
       product_meta.findBy(text("Availability")).shouldBe(visible);
   }
}
