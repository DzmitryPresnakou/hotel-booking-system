package com.presnakov.hotelBookingSystem.entity;

import java.util.Arrays;
import java.util.Optional;

public enum RoomStatusEnum {
    AVAILABLE,
    UNAVAILABLE,
    BOOKED;

    public static Optional<RoomStatusEnum> find(String roomStatus) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(roomStatus.toUpperCase()))
                .findFirst();
    }
}