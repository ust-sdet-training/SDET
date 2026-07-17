package com.tripstack.tests.ops;

import com.tripstack.base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@Tag("api")
@Tag("ops")
public class ResetNamespaceTest extends BaseTest {

    @Test
    void resetNamespaceSucceedsForOwnEmpId() {
        // Known issue: purged count is not deterministic in a shared env.
        // Assert only the contract that matters: success + correct namespace.
        opsClient.resetNamespace(token).then()
                .statusCode(200)
                .body("emp", equalTo("1014"));
    }
}