package ust.gama.sdet.Gate4.selenide.pages;


import com.codeborne.selenide.WebDriverRunner;
import ust.gama.sdet.Gate4.selenide.pages.SelenideComponents.ProductCard;
import ust.gama.sdet.Gate4.selenide.helper.Config;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

//@ExtendWith(ScreenShooterExtension.class)

public class CatalogPage {
    private static final String SEARCH_BOX = "[data-test='search-input']";
    private static final String RESULT_COUNT = "[data-test='catalog-result-count']";
    private static final String CARDS = "[data-test='product-card']";
    private static final String FIRST_LINK = "[data-test='product-card'] a";
    private static final String SORT_BY = "[data-test='sort-select']";
    private static final String EMPTY_SEARCH = "[data-test='empty-search']";


    public CatalogPage openCatalogPage(){
        open(Config.catalogURL());
        $(SEARCH_BOX).shouldBe(visible);
        return this;
    }

    public String isThisCatalog(){
        return WebDriverRunner.url();
    }

    public CatalogPage searchFor(String query){

        $(SEARCH_BOX).setValue(query).pressEnter();
        $$(CARDS).shouldHave(sizeGreaterThan(0));
        return this;
    }

    public CatalogPage searchFor(String query, String expectedResultCount){
        searchFor(query);
        $(RESULT_COUNT).shouldHave(text(expectedResultCount));
        return this;
    }

    public CatalogPage sortBy(String visibleText){
        String firstProductTitle = $$(CARDS).first().getText();
        $(SORT_BY).selectOption(visibleText);
        $$(CARDS).first().shouldNotHave(text(firstProductTitle));
        $$(CARDS).shouldHave(sizeGreaterThan(0));
        return this;
    }

    public CatalogPage productRows(int id){
        $$(CARDS).shouldHave(size(id));
        return this;
    }


    public CatalogPage textsInOrder(List<String> list){
        $$(CARDS).shouldHave(texts(list.toArray(new String[0])));
        return this;
    }

    public CatalogPage filterUsingSelenide(String keyword, int quantity){
        $$(CARDS).filterBy(text(keyword)).shouldHave(size(quantity));
        return this;
    }

    public List<ProductCard> cards(){
        return $$(CARDS).stream().map(ProductCard::new).toList();
    }


    public List<String> getTitles(){
        return cards().stream().map(ProductCard::title).toList();
    }

    public List<Integer> prices(){
        return cards().stream().map(ProductCard::price).toList();
    }

    public String emptySearch(){
        return $(EMPTY_SEARCH).getText();
    }

    public ProductPage openFirstProduct(){
        $(FIRST_LINK).click();
        return page(ProductPage.class);
    }

}

