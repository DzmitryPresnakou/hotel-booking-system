package com.presnakov.hotelBookingSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomOrder {
    private Integer id;
    private User user;
    private Room room;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
}