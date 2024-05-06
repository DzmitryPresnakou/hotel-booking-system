package com.presnakov.hotelBookingSystem.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;
    Integer userRole;
    String isActive;
}
