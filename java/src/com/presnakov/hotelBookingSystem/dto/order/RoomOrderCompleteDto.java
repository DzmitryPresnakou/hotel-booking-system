package com.presnakov.hotelBookingSystem.dto.order;

import com.presnakov.hotelBookingSystem.dto.room.RoomCompleteDto;
import com.presnakov.hotelBookingSystem.dto.user.UserDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class RoomOrderCompleteDto {
    Integer id;
    UserDto userDto;
    RoomCompleteDto roomCompleteDto;
    OrderStatusDto orderStatusDto;
    PaymentStatusDto paymentStatusDto;
    LocalDate checkInDate;
    LocalDate checkOutDate;
}