package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CatalogPage {

    private final SelenideElement searchBox =
            $("#search-products");

    private final ElementsCollection productCards =
            $$("[data-test='product-card']");

    public CatalogPage searchFor(String product) {

        searchBox.shouldBe(visible)
                .setValue(product)
                .pressEnter();

        return this;
    }

    public ProductPage viewProduct(String product) {

        productCards.findBy(text(product))
                .$("a")
                .click();

        return page(ProductPage.class);
    }
}