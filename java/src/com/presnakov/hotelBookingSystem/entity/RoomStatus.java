package com.presnakov.hotelBookingSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomStatus {
    private Integer id;
    private RoomStatusEnum roomStatusEnum;
}