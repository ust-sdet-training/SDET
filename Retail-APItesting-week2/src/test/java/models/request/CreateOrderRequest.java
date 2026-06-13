package models.request;

import java.util.List;

public class CreateOrderRequest {

    private List<Integer> items;
    private String currency;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(
            List<Integer> items,
            String currency) {

        this.items = items;
        this.currency = currency;
    }

    public List<Integer> getItems() {
        return items;
    }

    public String getCurrency() {
        return currency;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}