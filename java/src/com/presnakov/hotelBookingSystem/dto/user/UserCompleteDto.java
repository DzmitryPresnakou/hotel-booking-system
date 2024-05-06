package com.presnakov.hotelBookingSystem.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCompleteDto {
    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;
    UserRoleDto userRoleDto;
    Boolean isActive;
}