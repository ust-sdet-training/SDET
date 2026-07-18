package com.api.model;

public class Booking {

    private Long id;
    private String pnr;
    private String state;
    private String inventoryId;
    private String empId;

    public Booking() {
    }

    public Booking(Long id,
                   String pnr,
                   String state,
                   String inventoryId,
                   String empId) {

        this.id = id;
        this.pnr = pnr;
        this.state = state;
        this.inventoryId = inventoryId;
        this.empId = empId;
    }

    public Long getId() {
        return id;
    }

    public String getPnr() {
        return pnr;
    }

    public String getState() {
        return state;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public String getEmpId() {
        return empId;
    }
}