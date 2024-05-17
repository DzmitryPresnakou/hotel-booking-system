package com.presnakov.hotelBookingSystem.dto.order;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateOrderDto {
    Integer id;
    Integer user;
    Integer room;
    Integer orderStatus;
    Integer paymentStatus;
    String checkInDate;
    String checkOutDate;
}