package com.presnakov.hotelBookingSystem.dto.user;

import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserRoleDto {
    Integer id;
    UserRoleEnum userRoleEnum;
}