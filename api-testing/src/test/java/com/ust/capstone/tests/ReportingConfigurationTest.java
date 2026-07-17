package com.ust.capstone.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Framework Hardening")
@Feature("Reporting")
@Owner("akhil")
public class ReportingConfigurationTest {

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify categories.json exists.")

    void categoriesFileShouldExist() {

        Path path = Path.of(
                "src/test/resources/allure/categories.json"
        );

        assertTrue(
                Files.exists(path),
                "categories.json should exist"
        );
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify required categories exist.")

    void requiredCategoriesShouldExist() throws Exception {

        String categories = Files.readString(
                Path.of("src/test/resources/allure/categories.json")
        );

        assertAll(

                () -> assertTrue(categories.contains("Assertion Failures")),

                () -> assertTrue(categories.contains("UI Automation Issues")),

                () -> assertTrue(categories.contains("Database Issues")),

                () -> assertTrue(categories.contains("Configuration Issues")),

                () -> assertTrue(categories.contains("Infrastructure Issues")),

                () -> assertTrue(categories.contains("Other Failures"))

        );
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.NORMAL)
    @Description("Specific categories should appear before generic category.")

    void specificCategoriesShouldComeBeforeOtherFailures()
            throws Exception {

        String categories = Files.readString(
                Path.of("src/test/resources/allure/categories.json")
        );

        int assertion =
                categories.indexOf("Assertion Failures");

        int ui =
                categories.indexOf("UI Automation Issues");

        int database =
                categories.indexOf("Database Issues");

        int configuration =
                categories.indexOf("Configuration Issues");

        int infrastructure =
                categories.indexOf("Infrastructure Issues");

        int other =
                categories.indexOf("Other Failures");

        assertTrue(assertion < other);

        assertTrue(ui < other);

        assertTrue(database < other);

        assertTrue(configuration < other);

        assertTrue(infrastructure < other);
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify important regex patterns exist.")

    void regexPatternsShouldExist() throws Exception {

        String categories = Files.readString(
                Path.of("src/test/resources/allure/categories.json")
        );

        assertAll(

                () -> assertTrue(
                        categories.contains("AssertionFailedError")
                ),

                () -> assertTrue(
                        categories.contains("NoSuchElementException")
                ),

                () -> assertTrue(
                        categories.contains("SQLException")
                ),

                () -> assertTrue(
                        categories.contains("IllegalArgumentException")
                )

        );
    }
}
