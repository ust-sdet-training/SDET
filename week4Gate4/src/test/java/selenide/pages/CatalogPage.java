package selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class CatalogPage {
    private static final SelenideElement searchBox = $("[data-test='search-input']");
    private static final ElementsCollection productNames = $$("[data-test='product-title']");
    public CatalogPage open(){
        Selenide.open("/catalog");
        return this;
    }
    public CatalogPage searchFor(String query){
        searchBox.shouldBe(visible).setValue(query).pressEnter();
        return this;
    }
    public ElementsCollection results(){
        return productNames;
    }
    public CatalogPage openItem(){
        $$(".product-card .button.primary").first().click();
        return this;
    }
}
