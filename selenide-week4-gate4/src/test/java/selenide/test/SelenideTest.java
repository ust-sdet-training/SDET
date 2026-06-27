    package selenide.test;

    import org.junit.jupiter.api.BeforeAll;
    import org.junit.jupiter.api.Test;
    import selenide.pages.ProductPage;
    import selenide.support.SelenideConfig;

    import static com.codeborne.selenide.Condition.text;
    import static com.codeborne.selenide.Condition.visible;
    import static com.codeborne.selenide.Selenide.*;


    public class SelenideTest {
        @BeforeAll
        static void setup() {
            SelenideConfig.init();
        }

        @Test
        void product() {

            open("/catalog");

            $("[data-test='product-card'] a")
                    .shouldBe(visible)
                    .click();

            ProductPage productPage = page(ProductPage.class);

            productPage
                    .open()
                    .pname()
                    .shouldBe(visible);

            productPage
                    .avb()
                    .shouldHave(text("In stock"));
        }


    }
