package com.presnakov.hotelBookingSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole userRole;
    private Boolean isActive;
}