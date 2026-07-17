package ust.sdet.tests;

import io.cucumber.java.eo.Se;
import org.junit.jupiter.api.Test;
import ust.sdet.Data.TestDataBuilder;
import ust.sdet.SpecFactory.ConfigSpec;
import ust.sdet.Util.Functions;


public class MainTest {

    ConfigSpec configSpec = new ConfigSpec();

    TestDataBuilder testDataBuilder = new TestDataBuilder();

    Functions utilFunctions = new Functions();

    @Test
    void Login(){

        String token = utilFunctions.getToken();

        System.out.println(token);

    }

    @Test
    void SearchFlights(){

        String token = utilFunctions.getToken();



    }

}
