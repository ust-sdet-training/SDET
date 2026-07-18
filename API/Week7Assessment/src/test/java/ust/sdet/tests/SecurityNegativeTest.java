package ust.sdet.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import ust.sdet.Data.TestDataBuilder;
import ust.sdet.SpecFactory.ConfigSpec;
import ust.sdet.Util.Functions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Flight Management Security")
@Feature("Security Negative Testing")
public class SecurityNegativeTest {

        ConfigSpec configSpec = new ConfigSpec();

        TestDataBuilder testDataBuilder = new TestDataBuilder();

        Functions utilFunctions = new Functions();

        @Test
        @Story("Priviledge Escalation")
        @Description("Validates the User cannot access Higher Priviledged Endpoints")
        @Severity(SeverityLevel.BLOCKER)
        void checkPriviledgeEscalation(){

            String token = utilFunctions.getToken();
           Response response = utilFunctions.privilegeEscalationCall(token);
           assertThat(response.statusCode(),equalTo(403));
           assertThat(response.path("error"),equalTo("forbidden"));

        }

    }

