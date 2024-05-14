package com.presnakov.hotelBookingSystem.dto.room;

import com.presnakov.hotelBookingSystem.entity.RoomClassEnum;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class RoomClassDto {
    Integer id;
    RoomClassEnum comfortClass;
    BigDecimal pricePerDay;
}
