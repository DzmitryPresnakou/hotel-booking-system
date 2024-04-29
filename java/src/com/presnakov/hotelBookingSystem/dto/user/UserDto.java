package com.presnakov.hotelBookingSystem.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Integer id;
    String email;
}