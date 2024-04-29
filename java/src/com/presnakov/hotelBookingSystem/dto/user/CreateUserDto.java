package com.presnakov.hotelBookingSystem.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String firstName;
    String lastName;
    String email;
    String password;
    String userRole;
    String isActive;
}
