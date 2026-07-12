package com.shopkart.data.tests;



import io.qameta.allure.*;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Framework Hardening")
@Feature("Reporting Insights")
@Owner("SDET Trainee")
class AllureReporting {

    private static final Logger log =
            LoggerFactory.getLogger(AllureReporting.class);
    // Verify that the Allure category configuration correctly
// classifies flaky, test, and product defects.
    @Test
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Categories split flaky,test and product")
    void categoriesPutGenericFlakyRules() throws IOException{
        log.info("Verify that the Categories ensures the Generic Flaky Rules");

        String categories = Files.readString(Path.of("src/test/resources/categories.json"));

        int flakyIndex = categories.indexOf("\"Flaky tests\"");
        int testDefectIndex = categories.indexOf("\"Test defects (broken)\"");
        int productDefectIndex = categories.indexOf("\"Product defects\"");

        assertTrue(flakyIndex >=0);
        assertTrue(testDefectIndex > flakyIndex);
        assertTrue(productDefectIndex > flakyIndex);
        assertTrue(categories.contains("\"flaky\": true"));
        assertTrue(categories.contains("timeout|stale element|connection reset"));
    }
}

