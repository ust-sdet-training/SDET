package com.ust.sdet.api.apiframework.model;

import java.math.BigDecimal;

public record OrderDto(long id, String status, BigDecimal total) {
}
