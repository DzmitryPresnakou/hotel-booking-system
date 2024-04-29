package com.presnakov.hotelBookingSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    private Integer id;
    private Integer roomOccupancy;
    private RoomClass roomClass;
    private RoomStatus roomStatus;
    private Hotel hotel;
}