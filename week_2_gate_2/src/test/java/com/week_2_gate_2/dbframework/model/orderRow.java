package com.week_2_gate_2.dbframework.model;

import java.math.BigDecimal;
import java.time.Instant;

public record  orderRow(long id,String orderNumber,String status,BigDecimal total,String userId,Instant createdAT) {
}

 