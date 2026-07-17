package com.tripstack.resilience;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Minimal circuit breaker: CLOSED -> OPEN after N consecutive failures,
 * OPEN -> HALF_OPEN after a cooldown, HALF_OPEN -> CLOSED on a successful
 * probe or back to OPEN on a failed probe.
 */
public class CircuitBreaker {

    public enum State { CLOSED, OPEN, HALF_OPEN }

    private final int failureThreshold;
    private final long openDurationMs;

    private State state = State.CLOSED;
    private int consecutiveFailures = 0;
    private long openedAt = 0L;

    public CircuitBreaker(int failureThreshold, long openDurationMs) {
        this.failureThreshold = failureThreshold;
        this.openDurationMs = openDurationMs;
    }

    public State getState() {
        if (state == State.OPEN && System.currentTimeMillis() - openedAt >= openDurationMs) {
            state = State.HALF_OPEN;
        }
        return state;
    }

    public <T> T call(Supplier<T> action, Predicate<T> isFailure) {
        if (getState() == State.OPEN) {
            throw new IllegalStateException("BREAKER_OPEN: call short-circuited, retry after cooldown");
        }

        T result = action.get();

        if (isFailure.test(result)) {
            consecutiveFailures++;
            if (state == State.HALF_OPEN || consecutiveFailures >= failureThreshold) {
                trip();
            }
        } else {
            reset();
        }
        return result;
    }

    private void trip() {
        state = State.OPEN;
        openedAt = System.currentTimeMillis();
    }

    private void reset() {
        state = State.CLOSED;
        consecutiveFailures = 0;
    }
}