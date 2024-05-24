package com.presnakov.hotelBookingSystem.dto.room;

import com.presnakov.hotelBookingSystem.entity.RoomStatusEnum;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomStatusDto {
    Integer id;
    RoomStatusEnum roomStatusEnum;
}
