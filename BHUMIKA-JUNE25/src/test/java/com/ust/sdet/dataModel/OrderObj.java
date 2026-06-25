package com.ust.sdet.dataModel;

    public record OrderObj(
            int id,
            int petId,
            int quantity,
            String shipDate,
            String status,
            boolean complete
    ) {
    }
