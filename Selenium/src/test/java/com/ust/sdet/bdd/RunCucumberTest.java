package com.ust.sdet.bdd;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.ust.sdet.bdd")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value =
        "pretty,summary,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm," +
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")
public class RunCucumberTest {
}
