package ust.gama.sdet.Gate4.selenide.pages.SelenideComponents;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;

public class ProductCard {

    private final SelenideElement root;

    public ProductCard(SelenideElement root){
        this.root=root;
    }

    public String title(){
        return root.$("[data-test='product-title']").getText();
    }

    public int price(){
        return Integer.parseInt(root.$("[data-test='product-price']").getText()
                .replaceAll("[^0-9]",""));
    }



}

