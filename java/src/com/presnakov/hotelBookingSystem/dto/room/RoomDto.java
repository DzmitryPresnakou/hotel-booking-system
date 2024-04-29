package com.presnakov.hotelBookingSystem.dto.room;

import lombok.*;

@Value
@Builder
public class RoomDto {

    Integer id;
    String description;
}