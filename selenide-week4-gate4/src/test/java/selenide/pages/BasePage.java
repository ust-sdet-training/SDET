package selenide.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BasePage {

    public void openPage(String url) {
        open(url);
    }

    public void verifyPageLoaded(String locator) {
        $(locator).shouldBe(visible);
    }

}
