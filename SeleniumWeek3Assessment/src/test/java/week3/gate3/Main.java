package week3.gate3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import week3.gate3.support.Config;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        driver.get(Config.baseUrl());
        System.out.println(driver.getTitle());
        driver.quit();
    }
}