package com.presnakov.hotelBookingSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomClass {
    private Integer id;
    private RoomClassEnum comfortClass;
    private BigDecimal pricePerDay;
}