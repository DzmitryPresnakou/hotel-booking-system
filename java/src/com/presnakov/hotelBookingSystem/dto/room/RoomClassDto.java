package com.presnakov.hotelBookingSystem.dto.room;

import com.presnakov.hotelBookingSystem.entity.RoomClassEnum;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomClassDto {
    Integer id;
    RoomClassEnum comfortClass;
    Double pricePerDay;
}
