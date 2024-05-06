package com.presnakov.hotelBookingSystem.entity;

import java.util.Arrays;
import java.util.Optional;

public enum PaymentStatusEnum {
    APPROVED,
    DECLINED;

    public static Optional<PaymentStatusEnum> find(String paymentStatus) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(paymentStatus.toUpperCase()))
                .findFirst();
    }
}