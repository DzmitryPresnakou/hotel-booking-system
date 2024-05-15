package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.UserDao;
import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;
import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.dto.user.UserRoleDto;
import com.presnakov.hotelBookingSystem.entity.User;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.mapper.CreateUserMapper;
import com.presnakov.hotelBookingSystem.mapper.UserMapper;
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
    private final UserMapper userMapper = UserMapper.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<UserCompleteDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(userMapper::mapFrom);
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
                .map(UserService::getUserCompleteDto)
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
        return user.map(UserService::getUserCompleteDto).orElse(null);
    }

    public UserCompleteDto getUserByEmail(String email) {
        Optional<User> user = userDao.findByEmail(email);
        return user.map(UserService::getUserCompleteDto).orElse(null);
    }

    private static UserCompleteDto getUserCompleteDto(User user) {
        return UserCompleteDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .userRoleDto(UserRoleDto.builder()
                        .id(user.getUserRole().getId())
                        .userRoleEnum(user.getUserRole().getUserRoleEnum())
                        .build())
                .isActive(user.getIsActive())
                .build();
    }
}