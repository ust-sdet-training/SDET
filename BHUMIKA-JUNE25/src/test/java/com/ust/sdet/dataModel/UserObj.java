package com.ust.sdet.dataModel;

    public record UserObj(
            int id,
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            int userStatus
    ) {
    }

