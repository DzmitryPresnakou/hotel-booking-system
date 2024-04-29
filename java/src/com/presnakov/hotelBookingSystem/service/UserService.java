package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.UserDao;
import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;
import com.presnakov.hotelBookingSystem.dto.user.UserDto;
import com.presnakov.hotelBookingSystem.entity.User;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.mapper.CreateUserMapper;
import com.presnakov.hotelBookingSystem.validator.CreateUserValidator;
import com.presnakov.hotelBookingSystem.validator.ValidationResult;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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
//        if (!validationResult.isValid()) {
//            throw new ValidationException(validationResult.getErrors());
//        }
        User userEntity = createUserMapper.mapFrom(userDto);
        userDao.save(userEntity);
        return userEntity.getId();
    }
}
