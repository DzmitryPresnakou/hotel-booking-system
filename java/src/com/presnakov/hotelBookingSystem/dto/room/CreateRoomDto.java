package com.presnakov.hotelBookingSystem.dto.room;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRoomDto {
    Integer id;
    Integer occupancy;
    Integer roomClassDto;
    Integer roomStatusDto;
    Integer hotelDto;
}
