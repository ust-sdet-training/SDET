package ust.sdet.api;

import io.restassured.response.Response;
import ust.sdet.db.config.DbSupport;
import ust.sdet.db.config.DbSupport.*;
import ust.sdet.db.model.OrderRow;

import java.sql.SQLException;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DBFunctions {

    public static void validateDBStatus(Response response, String status) throws SQLException {

        OrderRow order = DbSupport.findOrder(Integer.toString(response.path("id")));

        assertEquals(order.status(),status);

        assertNotNull(order.createdAt());


    }

}
