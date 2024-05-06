package com.presnakov.hotelBookingSystem.entity;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatusEnum {
    OPEN,
    CLOSED,
    APPROVED,
    REJECTED;

    public static Optional<OrderStatusEnum> find(String orderStatus) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(orderStatus.toUpperCase()))
                .findFirst();
    }
}