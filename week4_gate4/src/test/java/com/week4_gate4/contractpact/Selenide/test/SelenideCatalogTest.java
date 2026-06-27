package com.week4_gate4.contractpact.Selenide.test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.week4_gate4.contractpact.Selenide.page.SelenideCatalogPage;
import com.week4_gate4.contractpact.Selenide.support.Config;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.week4_gate4.contractpact.Selenide.page.SelenideCatalogPage.*;


public class SelenideCatalogTest {

    @BeforeEach
    void setUp()
    {
        Config.apply();
        open("/catalog");
    }

    @Test
    @DisplayName("Add a Product to the cart and conformed its order")
    void catalogtocart()
    {
        open("/catalog",SelenideCatalogPage.class).searchFor("running");
        first_card_viewbtn.click();
        detail_element.shouldBe(visible);
        detail_element.shouldHave(text("Running Shoes"));
        stock.shouldBe(visible);
        btn.should(visible,enabled).click();
    }

}
