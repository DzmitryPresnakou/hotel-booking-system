package com.presnakov.hotelBookingSystem.dto;

import lombok.*;

@Value
@Builder
public class RoomDto {

    Long id;
    String description;
}