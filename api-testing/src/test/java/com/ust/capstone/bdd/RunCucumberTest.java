package com.ust.capstone.bdd;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/login.feature")
@SelectClasspathResource("features/bus_search.feature")
@SelectClasspathResource("features/unauthorized.feature")
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "com.ust.capstone.stepdefs"
)

@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = """
                pretty,
                io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm,
                com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:
                """
)
public class RunCucumberTest {
}
