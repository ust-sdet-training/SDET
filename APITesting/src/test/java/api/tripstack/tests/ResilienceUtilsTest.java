package api.tripstack.tests;

import api.tripstack.utils.ResilienceUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResilienceUtilsTest {

    @Test
    void retriesWhenResultIsMarkedRetryable() {
        AtomicInteger attempts = new AtomicInteger();

        String result = ResilienceUtils.withRetry(
                () -> {
                    if (attempts.incrementAndGet() < 3) {
                        return "retry";
                    }
                    return "success";
                },
                3,
                1,
                value -> "retry".equals(value),
                throwable -> false
        );

        assertEquals("success", result);
        assertEquals(3, attempts.get());
    }

    @Test
    void throwsAfterMaxAttemptsWhenStillRetryable() {
        AtomicInteger attempts = new AtomicInteger();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                ResilienceUtils.withRetry(
                        () -> {
                            attempts.incrementAndGet();
                            throw new IllegalStateException("still failing");
                        },
                        2,
                        1,
                        value -> false,
                        throwable -> true
                )
        );

        assertEquals("still failing", exception.getMessage());
        assertEquals(2, attempts.get());
    }
}
