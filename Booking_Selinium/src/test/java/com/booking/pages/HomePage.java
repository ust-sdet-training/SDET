package com.booking.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class HomePage extends BasePage {

    private static final By DESTINATION_INPUT = By.name("ss");
    private static final By SEARCH_BTN = By.cssSelector("button[type='submit']");
    private static final By DATE_PICKER = By.cssSelector("div[data-testid='searchbox-dates']");
    private static final By ADD_ADULT = By.cssSelector("button[aria-label='Increase number of Adults']");
    private static final By ADD_CHILD = By.cssSelector("button[aria-label='Increase number of Children']");
    private static final By ADD_ROOM = By.cssSelector("button[aria-label='Increase number of Rooms']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.booking.com");
        waitForElement(DESTINATION_INPUT);
    }

    public void enterDestination(String destination) {
        type(DESTINATION_INPUT, destination);
    }

    public void selectCheckInDate(String date) {
        click(DESTINATION_INPUT);
        waitForElement(DATE_PICKER);
        selectDate(date);
    }

    public void selectCheckOutDate(String date) {
        selectDate(date);
    }

    private void selectDate(String date) {
        By by;
        if (date != null && date.contains("-")) {
            by = By.cssSelector("[data-date='" + date + "']");
        } else {
            String day = date.split("/")[0].trim();
            by = By.xpath("//td//span[text()='" + day + "']");
        }
        click(by);
    }

    public void selectDates(String checkIn, String checkOut) {
        click(DESTINATION_INPUT);
        waitForElement(DATE_PICKER);
        selectDate(checkIn);
        selectDate(checkOut);
    }

    public void selectSimpleDates(String checkInDay, String checkOutDay) {
        click(DESTINATION_INPUT);
        waitForElement(DATE_PICKER);
        click(By.xpath("//td//span[text()='" + checkInDay + "']"));
        click(By.xpath("//td//span[text()='" + checkOutDay + "']"));
    }

    public void setguests(int adults, int children, int rooms) {
        click(DESTINATION_INPUT);
        int defaultAdults = 2;
        if (adults > defaultAdults) {
            for (int i = defaultAdults; i < adults; i++) {
                click(ADD_ADULT);
            }
        } else if (adults < defaultAdults) {}

        for (int i = 0; i < children; i++) {
            click(ADD_CHILD);
        }
        for (int i = 1; i < rooms; i++) {
            click(ADD_ROOM);
        }
    }

    public ResultsPage search() {
        click(SEARCH_BTN);
        return new ResultsPage(driver);
    }
}
