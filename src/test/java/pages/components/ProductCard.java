package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.CatalogPage;

import java.util.List;

public class ProductCard extends CatalogPage
{
    private static final By PRODUCT_CARD= By.cssSelector("[data-test='product-card']");
    private static final By CARD_TITLE= By.cssSelector("[data-test='product-title']");
    private static final By CARD_PRICE= By.cssSelector("[data-test='product-price']");
    private static final By CARD_NAME = By.cssSelector("[data-test = 'detail-name']");

    protected ProductCard(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> catalogCard()
    {
        return findby(PRODUCT_CARD);
    }

    public String catalogCardTitle()
    {
        return togetText(CARD_TITLE);
    }

    public int catalogCardPrice()
    {

        return cardPrice(CARD_PRICE);
    }


}
