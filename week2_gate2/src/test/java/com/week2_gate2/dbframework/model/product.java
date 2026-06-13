package com.week2_gate2.dbframework.model;

import java.math.BigDecimal;
import java.time.Instant;

public record product(long id, String orderNumber, String status, BigDecimal total, String userId, Instant createdAt) {}

