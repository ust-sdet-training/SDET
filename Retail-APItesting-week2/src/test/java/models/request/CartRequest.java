package models.request;

public class CartRequest {

    private int productId;
    private int quantity;
    private String size;
    private String color;
    private String fulfilment;

    public CartRequest() {
    }

    public CartRequest(int productId,
                       int quantity,
                       String size,
                       String color,
                       String fulfilment) {

        this.productId = productId;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.fulfilment = fulfilment;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFulfilment() {
        return fulfilment;
    }

    public void setFulfilment(String fulfilment) {
        this.fulfilment = fulfilment;
    }
}