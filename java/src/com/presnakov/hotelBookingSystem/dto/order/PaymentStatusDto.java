package com.presnakov.hotelBookingSystem.dto.order;

import com.presnakov.hotelBookingSystem.entity.PaymentStatusEnum;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentStatusDto {
    Integer id;
    PaymentStatusEnum paymentStatusEnum;
}