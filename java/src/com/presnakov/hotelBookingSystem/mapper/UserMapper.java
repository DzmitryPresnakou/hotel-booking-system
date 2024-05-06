package com.presnakov.hotelBookingSystem.mapper;

import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.dto.user.UserRoleDto;
import com.presnakov.hotelBookingSystem.entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<User, UserCompleteDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserCompleteDto mapFrom(User object) {
        return UserCompleteDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .password(object.getPassword())
                .userRoleDto(UserRoleDto.builder()
                        .id(object.getUserRole().getId())
                        .userRoleEnum(object.getUserRole().getUserRoleEnum())
                        .build())
                .isActive(object.getIsActive())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}