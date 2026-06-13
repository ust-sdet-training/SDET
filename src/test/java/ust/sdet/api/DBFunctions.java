package ust.sdet.api;

import io.restassured.response.Response;
import ust.sdet.db.config.DbSupport;
import ust.sdet.db.config.DbSupport.*;
import ust.sdet.db.model.OrderRow;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBFunctions {

    public static void validateDBStatus(Response response, String status) throws SQLException {

        OrderRow order = DbSupport.findOrder(Integer.toString(response.path("id")));

        assertEquals(order.status(),status);


    }

}
