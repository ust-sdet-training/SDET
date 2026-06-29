package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage
{

    private static By search = By.cssSelector("[data-backpack-ds-component='SelectableChip']");

    protected HomePage(WebDriver driver) {
        super(driver);
    }

    public void home() {

        location();

    }

    public void search(String query)
    {
        var searchBar = visible(search);
        searchBar.clear();
        searchBar.sendKeys(query);
        searchBar.sendKeys(Keys.ENTER);
    }

}


