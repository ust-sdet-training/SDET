package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderResponse(
        long id,
        long orderId,
        String orderNumber,
        String status
) {
}