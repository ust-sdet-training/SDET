import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class CartManagementTest {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void openAmazon() {
        driver.get("https://www.amazon.in");
    }

    @Test(priority = 2)
    public void searchProduct() throws InterruptedException {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("USB Keyboard");
        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(5000);
    }

    @Test(priority = 3)
    public void addFirstProduct() throws InterruptedException {
        List<WebElement> buttons = driver.findElements(By.xpath("//button[contains(.,'Add to cart')]"));
        buttons.get(0).click();
        Thread.sleep(3000);
    }

    @Test(priority = 4)
    public void returnToSearchResults() {
        Assert.assertTrue(driver.getCurrentUrl().contains("USB"));
    }

    @Test(priority = 5)
    public void addSecondProduct() throws InterruptedException {
        List<WebElement> buttons = driver.findElements(By.xpath("//button[contains(.,'Add to cart')]"));
        buttons.get(1).click();
        Thread.sleep(3000);
    }

    @Test(priority = 6)
    public void openCart() throws InterruptedException {
        driver.findElement(By.id("nav-cart")).click();
        Thread.sleep(3000);
    }

    @Test(priority = 7)
    public void validateBothProducts() {
        List<WebElement> products = driver.findElements(By.xpath("//input[@value='Delete']"));
        Assert.assertEquals(products.size(), 2);
    }

    @Test(priority = 8)
    public void removeOneProduct() throws InterruptedException {
        List<WebElement> deleteButtons = driver.findElements(By.xpath("//input[@value='Delete']"));
        deleteButtons.get(0).click();
        Thread.sleep(3000);
    }

    @Test(priority = 9)
    public void validateRemainingProduct() {
        List<WebElement> products = driver.findElements(By.xpath("//input[@value='Delete']"));
        Assert.assertEquals(products.size(), 1);
    }

    @Test(priority = 10)
    public void validateCartCount() {
        String cartCount = driver.findElement(By.id("nav-cart-count")).getText();
        Assert.assertEquals(cartCount, "1");
    }

    @Test(priority = 11)
    public void verifyCartConsistency() throws InterruptedException {
        driver.navigate().refresh();
        Thread.sleep(3000);

        List<WebElement> products = driver.findElements(By.xpath("//input[@value='Delete']"));
        String cartCount = driver.findElement(By.id("nav-cart-count")).getText();

        Assert.assertEquals(products.size(), 1);
        Assert.assertEquals(cartCount, "1");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}