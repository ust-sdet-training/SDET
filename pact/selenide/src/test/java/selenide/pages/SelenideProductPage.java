package selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SelenideProductPage {
    private final SelenideElement heading =
            $("#catalog-title");

    private SelenideElement price=$(".price");
    private SelenideElement addToCart =
            $("[data-test='add-to-cart']");

    private final SelenideElement searchBox =
            $("#search-products");

    private final ElementsCollection productCards =
            $$(".product-card");

    private final ElementsCollection productNames =
            $$("[data-test='product-title']");

    private final SelenideElement productTitle =
            $("[data-test='detail-name']");


    public void openCatalog() {
        open("/catalog");
    }

    public SelenideElement heading() {
        return heading;
    }

    public SelenideProductPage search(String product) {

        searchBox.shouldBe(visible)
                .setValue(product)
                .pressEnter();

        return this;
    }

    public ElementsCollection results() {
        return productCards;
    }

    public ElementsCollection productNames() {
        return productNames;
    }

    public void clickProduct(String product) {

        productCards
                .filterBy(text(product))
                .first()
                .$("a.button.primary")
                .click();
    }

    public SelenideElement productTitle() {
        return productTitle;
    }

    public String getMetaValue(String label) {

        return $$("dl.product-meta > div")
                .findBy(text(label))
                .$("dd")
                .shouldBe(visible)
                .getText();
    }

    public SelenideElement price() {
        return price;
    }

    public void addToCart() {
        addToCart.shouldBe(visible).click();
    }


}
