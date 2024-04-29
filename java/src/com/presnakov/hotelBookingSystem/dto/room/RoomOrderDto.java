package com.presnakov.hotelBookingSystem.dto.room;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomOrderDto {

    Integer id;
    Integer roomId;
    Integer orderStatusId;
}