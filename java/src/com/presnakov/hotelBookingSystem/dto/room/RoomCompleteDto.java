package com.presnakov.hotelBookingSystem.dto.room;

import com.presnakov.hotelBookingSystem.dto.hotel.HotelDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomCompleteDto {
    Integer id;
    Integer occupancy;
    RoomClassDto roomClassDto;
    RoomStatusDto roomStatusDto;
    HotelDto hotelDto;
}