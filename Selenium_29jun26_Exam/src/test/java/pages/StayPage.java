package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import javax.management.Query;

public class StayPage  extends  BasePage{

    private static By place = By.cssSelector("[data-testid='destination-autosuggest']");
    private static By search = By.cssSelector("[data-tracking-element-id='hotel_search_control_button']");


    protected StayPage(WebDriver driver) {
        super(driver);
    }

    public void Place(String query) {
        var search = visible(place);
        search.clear();
        search.sendKeys(query);
        search.sendKeys(Keys.ENTER);
    }

    public void checkin(String query) {

        click(search);

    }



}
