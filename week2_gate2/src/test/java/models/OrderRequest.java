package models;

import java.util.List;

public class OrderRequest {

    private List<Integer> items;

    public OrderRequest() {
    }

    public OrderRequest(List<Integer> items) {
        this.items = items;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<Integer> items;

        public Builder items(List<Integer> items) {
            this.items = items;
            return this;
        }

        public OrderRequest build() {
            return new OrderRequest(items);
        }
    }
}