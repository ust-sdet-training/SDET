package week3.bdd;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/EtoEFlow.feature")
@SelectClasspathResource("features/cartbadge.feature")
@SelectClasspathResource("features/severalProducts.feature")
@SelectClasspathResource("features/FreshCart.feature")
@SelectClasspathResource("features/negative.feature")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "week3.bdd" )
public class RunCucumberTest {

}
