package com.presnakov.hotelBookingSystem.dto.room;

import com.presnakov.hotelBookingSystem.entity.RoomClass;
import com.presnakov.hotelBookingSystem.entity.RoomStatus;

public record RoomFilter(Integer roomOccupancy,
                         RoomClass roomClass,
                         RoomStatus roomStatus,
                         Integer hotelId) {
}