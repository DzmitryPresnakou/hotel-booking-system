package com.presnakov.hotelBookingSystem.entity;

import java.math.BigDecimal;

public record RoomClass(Long id,
                        String comfortClass,
                        BigDecimal pricePerDay) {
}
