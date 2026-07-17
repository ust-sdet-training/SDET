package api.tripstack.utils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ResilienceUtils {
    private ResilienceUtils() {
    }

    public static <T> T withRetry(
            Supplier<T> action,
            int maxAttempts,
            long backoffMillis,
            Function<T, Boolean> retryCondition,
            Function<Throwable, Boolean> shouldRetryOnException
    ) {
        Throwable lastException = null;
        T lastValue = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                T result = action.get();
                if (retryCondition == null || !retryCondition.apply(result)) {
                    return result;
                }
                lastValue = result;
            } catch (Throwable throwable) {
                lastException = throwable;
                if (shouldRetryOnException == null || !shouldRetryOnException.apply(throwable) || attempt >= maxAttempts) {
                    if (throwable instanceof RuntimeException runtimeException) {
                        throw runtimeException;
                    }
                    if (throwable instanceof Error error) {
                        throw error;
                    }
                    throw new RuntimeException(throwable);
                }
            }

            if (attempt < maxAttempts) {
                sleep(backoffMillis);
            }
        }

        if (lastException != null) {
            if (lastException instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            if (lastException instanceof Error error) {
                throw error;
            }
            throw new RuntimeException(lastException);
        }
        return lastValue;
    }

    private static void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted during retry sleep", e);
        }
    }
}
