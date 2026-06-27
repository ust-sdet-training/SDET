package Base;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {

    protected static final String BASE_URL = "http://localhost:5173";
    protected static final String CATALOG_URL = BASE_URL + "/catalog";

    @BeforeEach
    void setUp() {

        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";

        open(CATALOG_URL);
    }
}