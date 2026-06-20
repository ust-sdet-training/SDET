package com.sdet.selenium.bdd;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/checkout.feature")
//@SelectClasspathResource("features/login.feature")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value="com.sdet.selenium.bdd")
public class RunCucumberTest {

}
