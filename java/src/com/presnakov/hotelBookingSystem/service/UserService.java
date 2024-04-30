package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.UserDao;
import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;
import com.presnakov.hotelBookingSystem.dto.user.UserDto;
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

    public Integer create(CreateUserDto userDto) {
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User userEntity = createUserMapper.mapFrom(userDto);
        userDao.save(userEntity);
        return userEntity.getId();
    }

    public List<UserDto> findAll() {
        return userDao.findAll().stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .build())
                .collect(toList());
    }

    public boolean deleteUser(Integer id) {
        if (id < 0) {
            return false;
        }
        return userDao.delete(id);
    }

    public UserDto getUser(Integer id) {
        Optional<User> user = userDao.findById(id);
        return user.map(value -> UserDto.builder()
                .id(value.getId())
                .email(value.getEmail())
                .build()).orElse(null);
    }
}