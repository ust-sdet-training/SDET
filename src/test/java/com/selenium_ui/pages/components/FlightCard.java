package com.selenium_ui.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FlightCard {

    private final WebElement root;
    public FlightCard(WebElement root) {
        this.root = root;
    }

    public String airline() {
        return root.findElement(By.className("txt-r4")).getText();
    }
    public String departure() {
        return root.findElement(By.className("txt-r2-n")).getText();
    }
    public String arrival() {
        return root.findElements(By.className("txt-r2-n")).get(1).getText();
    }
    public String duration() {
        return root.findElement(By.className("duratn")).getText();
    }
    public double fare() {
        String fare = root.findElement(By.className("txt-r6")).getText();
        return Double.parseDouble(fare.replaceAll("[^0-9]", ""));
    }
}