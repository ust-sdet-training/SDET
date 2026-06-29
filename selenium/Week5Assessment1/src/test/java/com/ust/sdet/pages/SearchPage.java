package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchPage extends BasePage{

    private static final By SourceCityInput = By.cssSelector(
            "#txtSrcCity");
    private static final By DestinationCityInput = By.cssSelector(
            "#txtDesCity");

    private static final By DateInput = By.cssSelector(
            "#datepicker");

    private static final By DatePicker =  By.xpath("//a[text()='30']");

    private static final By SearchButton = By.cssSelector(
            "#srcbtn");




    public SearchPage(WebDriver driver){
        super(driver);
    }

    public SearchPage open(){
        openSearchPage();
        return this;
    }

    public void searchBus(String sourceCity,String destinationCity, String date){


        click(SourceCityInput);
        type(SourceCityInput,sourceCity);

        By optionsDest = By.xpath("//div[contains(@class,'auto_sugg_tttl') and contains(text(),'Chennai')]");

        click(optionsDest);


        By destinationInput = By.id("txtDesCity");

        click(destinationInput);

        type(destinationInput, destinationCity);

        By optionDestination = By.xpath("//div[contains(@class,'auto_sugg_tttl') and contains(text(),'Coimbatore')]");
        click(optionDestination);




        click(DateInput);
        click(DatePicker);

        click(SearchButton);



    }

    public void checkBusDetails(){


        By busName = By.cssSelector("div.c-nme");

        String title = visibleAll(busName).getFirst().getAttribute("title");



        By time = By.cssSelector("div.trns-tim");

        String timestr = visibleAll(time).getFirst().getText();

        System.out.println(timestr);

        List<WebElement> times = visibleAll(By.cssSelector("div.trns-tim"));


        String departure = times.get(0).getText();
        String arrival   = times.get(1).getText();

        assertEquals("zingbus plus",title);
        assertEquals("23:25",departure);
        assertEquals("07:10",arrival);

        System.out.println("Departure: " + departure);
        System.out.println("Arrival: " + arrival);




    }
}
