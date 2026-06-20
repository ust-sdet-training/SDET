package support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static support.Config.headless;

public class DriveFactory {

 private DriveFactory(){

 }

 public static WebDriver createChromeDriver()
 {
    ChromeOptions options= new ChromeOptions() ; // is needed to create ChromeDriver

     Map<String, Object> prefs = new HashMap<>();

     prefs.put("credentials_enable_service", false);

     prefs.put("profile.password_manager_enabled", false);

     prefs.put("profile.password_manager_leak_detection", false);

     options.setExperimentalOption("prefs", prefs);

    if(headless())                             // that is setting headless and window size
    {
        options.addArguments("--headless=new");
    }
        options.addArguments("--window size = 1440,900");

        return new ChromeDriver(options);
 }

}
