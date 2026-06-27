package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class CatalogPage {
    private final SelenideElement search = $("[data-test='search-input']");
    private final SelenideElement resultCount = $("[data-test='catalog-result-count']");
    private final ElementsCollection cards = $$("[data-test='product-card']");
    private final ElementsCollection productTitles = $$("[data-test='product-title']");

    public CatalogPage openPage() {
        open("/catalog");
        search.shouldBe(visible);
        return this;
    }

    public CatalogPage searchFor(String query) {
        search.shouldBe(visible).setValue(query).pressEnter();
        cards.shouldHave(sizeGreaterThan(0));
        return this;
    }

    public ElementsCollection results() {
        return cards;
    }

    public ElementsCollection productTitles() {
        return productTitles;
    }

    public SelenideElement resultCount() {
        return resultCount;
    }
}
