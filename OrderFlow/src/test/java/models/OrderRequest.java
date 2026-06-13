package models;

public record OrderRequest(
        String paymentMethod,
        String deliverySlot,
        String address,
        int shipping,
        int discount
) {
}