package com.presnakov.hotelBookingSystem.dto.order;

import com.presnakov.hotelBookingSystem.entity.OrderStatusEnum;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderStatusDto {
    Integer id;
    OrderStatusEnum orderStatusEnum;
}