package com.presnakov.hotelBookingSystem.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomOrderDto {

    Long id;
    Long roomId;
    Long orderStatusId;
}