package com.tripstack.tests.performance;


import com.tripstack.base.BaseTest;
import com.tripstack.config.ConfigManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("api")
@Tag("performance")
public class BusSearchPerformanceTest extends BaseTest {

    @Test
    void busSearchRespondsWithinThreshold() {
        int sampleSize = ConfigManager.PERF_SAMPLE_SIZE;
        long[] latencies = new long[sampleSize];

        for (int i = 0; i < sampleSize; i++) {
            long start = System.nanoTime();
            Response response = busClient.searchBus(token, ConfigManager.FROM_CITY, ConfigManager.TO_CITY, travelDate());
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            response.then().statusCode(200);
            latencies[i] = elapsedMs;
        }

        long sum = 0, max = Long.MIN_VALUE, min = Long.MAX_VALUE;
        for (long l : latencies) {
            sum += l;
            max = Math.max(max, l);
            min = Math.min(min, l);
        }
        double avg = (double) sum / sampleSize;

        System.out.printf(
                "PERF baseline [GET /api/buses]: samples=%d avg=%.1fms min=%dms max=%dms threshold=%dms%n",
                sampleSize, avg, min, max, ConfigManager.PERF_THRESHOLD_MS);

        assertTrue(avg <= ConfigManager.PERF_THRESHOLD_MS,
                String.format("Performance regression: avg latency %.1fms exceeded threshold %dms " +
                                "(min=%dms, max=%dms). Check the OpenTelemetry trace for GET /api/buses.",
                        avg, ConfigManager.PERF_THRESHOLD_MS, min, max));
    }
}