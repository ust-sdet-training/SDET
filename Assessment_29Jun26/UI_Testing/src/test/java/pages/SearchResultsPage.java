package pages;

import pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResultsPage extends BasePage {

    public SearchResultsPage(WebDriver driver){

        super(driver);
    }

    private By productBrands =
            By.cssSelector("h3.product-brand");

    private By productPrices =
            By.cssSelector("span.product-discountedPrice");

    private By sortMenu =
            By.cssSelector(".sort-sortBy");

    public void selectBrand(String brand){

        By locator = By.xpath("//label[contains(.,'" + brand + "')]");

        scrollTo(locator);

        click(locator);
    }

    public List<String> getDisplayedBrands(){

        List<String> brands = new ArrayList<>();

        for(WebElement e : driver.findElements(productBrands)){

            brands.add(e.getText());
        }

        return brands;
    }

    public void applyPriceFilter(){

        By locator =
                By.xpath("//label[contains(.,'Rs. 500 to Rs. 1000')]");

        scrollTo(locator);

        click(locator);
    }

    public List<Integer> getPrices(){

        List<Integer> prices = new ArrayList<>();

        List<WebElement> list =
                driver.findElements(productPrices);

        for(WebElement e:list){

            String price =
                    e.getText()
                            .replace("Rs. ","")
                            .replace(",","");

            prices.add(Integer.parseInt(price));
        }

        return prices;
    }

    public void sortLowToHigh(){

        hover(sortMenu);

        By option =
                By.xpath("//label[contains(text(),'Price: Low to High')]");

        click(option);

        wait.until(ExpectedConditions.visibilityOfElementLocated(productPrices));
    }

    public boolean pricesAreSorted(){

        List<Integer> actual = getPrices();

        List<Integer> expected =
                new ArrayList<>(actual);

        Collections.sort(expected);

        return actual.equals(expected);
    }
}