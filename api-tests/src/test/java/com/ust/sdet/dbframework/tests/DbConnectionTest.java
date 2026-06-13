package com.ust.sdet.dbframework.tests;

import com.ust.sdet.base.BaseDatabaseTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DbConnectionTest extends BaseDatabaseTest {
    @Test
    @DisplayName("Should connect to MySQL database")
    void shouldConnectToDatabase() throws Exception {
        boolean connected = db.isReachable();
        assertTrue(connected);
    }
}