package org.selenium.sdet.bdd;
import io.cucumber.core.backend.Glue;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/checkout.feature")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,value = "week3.gate3.bdd")
public class RunCucumberTest {

}

