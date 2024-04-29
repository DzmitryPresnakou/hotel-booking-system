package com.presnakov.hotelBookingSystem.dto.room;

public record RoomFilter(int limit,
                         int offset,
                         Integer roomOccupancy,
                         Integer roomClassId,
                         Integer roomStatusId,
                         Integer hotelId) {
}