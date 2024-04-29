package com.presnakov.hotelBookingSystem.entity;

import java.util.Arrays;
import java.util.Optional;

public enum UserRoleEnum {
    ADMIN,
    USER;

    public static Optional<UserRoleEnum> find(String role) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(role.toUpperCase()))
                .findFirst();
    }
}