package com.tripstack.tests.db;

import com.tripstack.support.DatabaseValidationHelper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class DatabaseIntegrationTest {

    private final DatabaseValidationHelper databaseValidationHelper = new DatabaseValidationHelper();

    @Test
    void shouldReadDataFromDatabase() throws Exception {
        int result = databaseValidationHelper.validateSimpleQuery();
        assertThat(result, equalTo(1));
    }
}
