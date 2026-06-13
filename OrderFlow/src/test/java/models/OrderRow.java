package models;

public class OrderRow {

    public int    id;
    public String status;
    public String orderNumber;

    public OrderRow(int id, String status, String orderNumber) {
        this.id          = id;
        this.status      = status;
        this.orderNumber = orderNumber;
    }
}