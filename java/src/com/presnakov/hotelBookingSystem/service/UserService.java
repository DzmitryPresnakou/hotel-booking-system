package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.UserDao;
import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;
import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.dto.user.UserRoleDto;
import com.presnakov.hotelBookingSystem.entity.User;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.mapper.CreateUserMapper;
import com.presnakov.hotelBookingSystem.validator.CreateUserValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Integer create(CreateUserDto createUserDto) {
        var validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User userEntity = createUserMapper.mapFrom(createUserDto);
        userDao.save(userEntity);
        return userEntity.getId();
    }

    public Integer update(CreateUserDto createUserDto) {
        var validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User userEntity = createUserMapper.mapFrom(createUserDto);
        userDao.update(userEntity);
        return userEntity.getId();
    }

    public List<UserCompleteDto> findAll() {
        return userDao.findAll().stream()
                .map(user -> UserCompleteDto.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .userRoleDto(UserRoleDto.builder()
                                .userRoleEnum(user.getUserRole().getUserRoleEnum())
                                .build())
                        .isActive(user.getIsActive())
                        .build())
                .collect(toList());
    }

    public boolean deleteUser(Integer id) {
        if (id < 0) {
            return false;
        }
        return userDao.softDelete(id);
    }

    public UserCompleteDto getUser(Integer id) {
        Optional<User> user = userDao.findById(id);
        return user.map(value -> UserCompleteDto.builder()
                .id(value.getId())
                .firstName(value.getFirstName())
                .lastName(value.getLastName())
                .email(value.getEmail())
                .password(value.getPassword())
                .userRoleDto(UserRoleDto.builder()
                        .userRoleEnum(value.getUserRole().getUserRoleEnum())
                        .build())
                .isActive(value.getIsActive())
                .build()).orElse(null);
    }
}