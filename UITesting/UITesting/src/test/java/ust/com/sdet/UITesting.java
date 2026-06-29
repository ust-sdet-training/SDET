package ust.com.sdet;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UITesting {
    WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void validateFilterAndSorting() throws InterruptedException {

        driver.get("https://www.amazon.in");

        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("headphones");
        driver.findElement(By.id("nav-search-submit-button")).click();
        driver.findElement(By.xpath("//span[text()='boAt']")).click();

        Thread.sleep(3000);

        Select sort = new Select(driver.findElement(By.id("s-result-sort-select")));
        sort.selectByVisibleText("Price: Low to High");
        Thread.sleep(5000);

        List<WebElement> prices = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']//span[@class='a-price-whole']"));
        List<Integer> actualPrices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String price = prices.get(i)
                    .getText()
                    .replace(",", "");
            actualPrices.add(Integer.parseInt(price));
        }

        System.out.println(actualPrices);
        List<Integer> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);

        Assertions.assertEquals(sortedPrices, actualPrices);
        System.out.println("Prices are sorted correctly");
        driver.findElement(By.xpath("(//div[@data-component-type='s-search-result'])[1]")).click();
        Thread.sleep(3000);
        String pageText = driver.getPageSource();
        Assertions.assertTrue(pageText.toLowerCase().contains("boat"));
        System.out.println("Brand verification passed");
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}