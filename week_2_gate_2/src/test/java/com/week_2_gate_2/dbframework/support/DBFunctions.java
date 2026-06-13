package com.week_2_gate_2.dbframework.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;

import com.week_2_gate_2.dbframework.model.orderRow;

import io.restassured.response.Response;

public class DBFunctions {
    public static void check(orderRow row,String status,String orderNumber,String userId,Response r){
        assertNotEquals(row, "");
        assertEquals(status, row.status());
        assertNotNull(orderNumber,row.orderNumber());
        assertEquals(0,row.total().compareTo(r.jsonPath().getObject("total", BigDecimal.class)));
        assertEquals(userId,row.userId());
        assertNotNull(row.createdAT());
        Instant createdOrderIds = row.createdAT()
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Instant now = Instant.now();
        assertTrue(createdOrderIds.isAfter(now.minusSeconds(60)));
        assertTrue(createdOrderIds.isBefore(now.plusSeconds(5)));
    }
}
