package com.presnakov.hotelBookingSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentStatus {
    private Integer id;
    private PaymentStatusEnum paymentStatusEnum;
}