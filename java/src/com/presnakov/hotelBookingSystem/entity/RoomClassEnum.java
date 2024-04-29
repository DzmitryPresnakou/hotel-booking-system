package com.presnakov.hotelBookingSystem.entity;

import java.util.Arrays;
import java.util.Optional;

public enum RoomClassEnum {
    ECONOMY,
    COMFORT,
    BUSINESS;

    public static Optional<RoomClassEnum> find(String roomClass) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(roomClass.toUpperCase()))
                .findFirst();
    }
}