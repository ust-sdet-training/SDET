package com.ust.sdet.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public final class CartModels {

    private CartModels() {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddCartItemRequest {

        private int productId;
        private int quantity;
        private String size;
        private String color;
        private String fulfilment;

        public AddCartItemRequest() {}

        public AddCartItemRequest(int productId, int quantity, String size, String color, String fulfilment) {
            this.productId = productId;
            this.quantity = quantity;
            this.size = size;
            this.color = color;
            this.fulfilment = fulfilment;
        }

        public int getProductId() {
            return productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getSize() {
            return size;
        }

        public String getColor() {
            return color;
        }

        public String getFulfilment() {
            return fulfilment;
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UpdateCartItemRequest {

        private int quantity;

        public UpdateCartItemRequest() {}

        public UpdateCartItemRequest(int quantity) {
            this.quantity = quantity;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}