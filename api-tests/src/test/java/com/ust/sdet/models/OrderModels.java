package com.ust.sdet.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
public final class OrderModels {
    private OrderModels() {
    }


    @JsonIgnoreProperties(
            ignoreUnknown = true
    )
    public static class CreateOrderRequest {
        private String paymentMethod;
        private String address;
        private String deliverySlot;
        public CreateOrderRequest() {
        }
        public CreateOrderRequest(String paymentMethod, String address, String deliverySlot) {
            this.paymentMethod = paymentMethod;
            this.address = address;
            this.deliverySlot = deliverySlot;

        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public String getAddress() {
            return address;
        }

        public String getDeliverySlot() {
            return deliverySlot;
        }

    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SecureOrderRequest {
        private List<Integer> items;
        public SecureOrderRequest() {
        }
        public SecureOrderRequest(List<Integer> items) {
            this.items = items;
        }
        public List<Integer> getItems() {
            return items;
        }

    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderResponse {
        private long id;
        private String status;
        private double total;
        public long getId() {
            return id;
        }
        public String getStatus() {
            return status;
        }
        public double getTotal() {
            return total;
        }

    }

}