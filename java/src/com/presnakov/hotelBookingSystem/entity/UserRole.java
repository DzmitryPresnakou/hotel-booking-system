package com.presnakov.hotelBookingSystem.entity;

import java.util.Objects;

public class UserRole {

    private Long id;
    private UserRoleEnum userRoleEnum;

    public UserRole(Long id, UserRoleEnum userRoleEnum) {
        this.id = id;
        this.userRoleEnum = userRoleEnum;
    }

    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRoleEnum getUserRoleEnum() {
        return userRoleEnum;
    }

    public void setUserRoleEnum(UserRoleEnum userRoleEnum) {
        this.userRoleEnum = userRoleEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) && userRoleEnum == userRole.userRoleEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userRoleEnum);
    }

    @Override
    public String toString() {
        return "UserRole{" +
               "id=" + id +
               ", userRoleEnum=" + userRoleEnum +
               '}';
    }
}