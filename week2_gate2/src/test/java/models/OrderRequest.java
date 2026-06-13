package models;

import java.util.List;

public class OrderRequest {

    private List<Integer> items;

    public OrderRequest(List<Integer> items) {
        this.items = items;
    }

    public List<Integer> getItems() {
        return items;
    }
}
