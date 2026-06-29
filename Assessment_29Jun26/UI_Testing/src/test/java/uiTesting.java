import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

public class uiTesting {
    @Test
    public void openPageAndVerifyTitle() throws InterruptedException {

    WebDriver driver = new ChromeDriver();
    driver.manage().window().maximize();

    driver.get("https://www.myntra.com/");
    assertTrue(driver.getTitle().contains("Myntra"));

    driver.findElement(By.className("desktop-searchBar")).click();
    driver.findElement(By.className("desktop-searchBar")).sendKeys("T-shirt");

    driver.findElement(By.className("desktop-searchBar")).sendKeys(Keys.ENTER);

    System.out.println(driver.getTitle());
    assertTrue(driver.getTitle().contains("T-Shirts "));
    driver.findElement(By.className("sort-sortBy")).click();
    //driver.findElement(By.tagName("price_asc")).click();
    driver.findElement(By.xpath("//*[@id=\"desktopSearchResults\"]/div[1]/section/div[1]/div[1]/div/div/div/ul/li[6]")).click();
    driver.findElement(By.className("img-responsive")).click();
    assertTrue(driver.getTitle().contains("Dressberry"));
    driver.quit();
}
}