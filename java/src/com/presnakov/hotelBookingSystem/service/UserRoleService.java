package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.UserRoleDao;
import com.presnakov.hotelBookingSystem.dto.user.UserRoleDto;
import com.presnakov.hotelBookingSystem.entity.UserRole;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;
import com.presnakov.hotelBookingSystem.mapper.CreateUserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRoleService {

    private static final UserRoleService INSTANCE = new UserRoleService();

    private final UserRoleDao userRoleDao = UserRoleDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

    public static UserRoleService getInstance() {
        return INSTANCE;
    }

    public List<UserRoleDto> findAll() {
        return userRoleDao.findAll().stream()
                .map(userRole -> UserRoleDto.builder()
                        .id(userRole.getId())
                        .userRoleEnum(userRole.getUserRoleEnum())
                        .build())
                .collect(toList());
    }

    public UserRoleDto getUserRole(Integer id) {
        Optional<UserRole> userRole = userRoleDao.findById(id);
        return userRole.map(value -> UserRoleDto.builder()
                .id(value.getId())
                .userRoleEnum(value.getUserRoleEnum())
                .build()).orElse(null);
    }

    public Integer getUserRoleId(UserRoleEnum role) {
        return userRoleDao.findByRole(role);
    }
}