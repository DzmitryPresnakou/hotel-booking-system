package com.presnakov.hotelBookingSystem.dto.hotel;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HotelDto {
    Integer id;
    String name;
}
