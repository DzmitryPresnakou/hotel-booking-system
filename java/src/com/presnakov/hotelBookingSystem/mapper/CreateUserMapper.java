package com.presnakov.hotelBookingSystem.mapper;

import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;
import com.presnakov.hotelBookingSystem.entity.User;
import com.presnakov.hotelBookingSystem.entity.UserRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDto, User>{

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(CreateUserDto object) {
        return User.builder()
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .password(object.getPassword())
                .userRole(UserRole.builder()
                        .id(Integer.valueOf(object.getUserRole()))
                        .build())
                .isActive(Boolean.valueOf(object.getIsActive()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}