package com.apitesting.performance;

import com.apitesting.api.AuthApi;
import com.apitesting.api.BusApi;
import com.apitesting.config.ApiConfig;
import com.apitesting.support.DateUtils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("performance")
class PerformanceTest {

    private static final int ITERATIONS = 10;
    private static final long THRESHOLD_MS = 1500;

    private static AuthApi authApi;
    private static BusApi busApi;
    private static String token;

    @BeforeAll
    static void setup() {
        authApi = new AuthApi();
        busApi = new BusApi();
        token = authApi.login(ApiConfig.EMAIL, ApiConfig.PASSWORD).token;
    }

    @Test
    void resultsListingShouldMeetLatencyThreshold() {
        String travelDate = DateUtils.travelDate();
        List<Long> latencies = new ArrayList<>();

        for (int i = 0; i < ITERATIONS; i++) {
            long start = System.currentTimeMillis();
            busApi.search(token, ApiConfig.ORIGIN, ApiConfig.DESTINATION, travelDate);
            latencies.add(System.currentTimeMillis() - start);
        }

        Collections.sort(latencies);
        long p95 = latencies.get((int) (latencies.size() * 0.95));

        System.out.println("Results listing p95: " + p95 + "ms (threshold: " + THRESHOLD_MS + "ms)");
        assertThat(p95).isLessThanOrEqualTo(THRESHOLD_MS);
    }
}