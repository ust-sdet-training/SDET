package pages;

import io.cucumber.messages.types.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CatalogPage extends BasePage
{

    private static final By CATALOG_TITLE = By.cssSelector("[data-test = 'catalog-title']");
    private static final By CATALOG_SEARCH = By.cssSelector("[data-test = 'search-input']");
    private static final By PRODUCT_LINK = By.cssSelector("[data-test = 'product-card'] a");
    private static final By CATALOG_COUNT= By.cssSelector("[data-test='catalog-result-count']");
    private static final By CATALOG_SORT= By.cssSelector("[data-test='sort-select']");

    public CatalogPage(WebDriver driver)
    {
        super(driver);
    }

    public CatalogPage catalogTitle()
    {
        find(CATALOG_TITLE);
        return this;
    }

    public CatalogPage open()
    {
         location();
         return this;
    }

    public CatalogPage searchFor(String query, String expectedCount)
    {
        WebElement search = visible(CATALOG_SEARCH);
        search.clear();
        search.sendKeys(query);
        search.sendKeys(Keys.ENTER);
        visibleAll(PRODUCT_LINK);
        return this;
    }

    public String catalogCount()
    {
        return togetText(CATALOG_COUNT);
    }

    public ProductPage goToproductPage()
    {
        click(PRODUCT_LINK);
        return new ProductPage(driver);
    }

    public ProductPage openFirstProduct()
    {
        visibleAll(PRODUCT_LINK).getFirst().click();
        return new ProductPage(driver);
    }

    public WebElement filter()
    {
        return find(CATALOG_SORT)  ;
    }


}
