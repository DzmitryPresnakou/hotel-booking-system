package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.OrderStatusDao;
import com.presnakov.hotelBookingSystem.dto.order.OrderStatusDto;
import com.presnakov.hotelBookingSystem.entity.OrderStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderStatusService {

    private static OrderStatusService INSTANCE = new OrderStatusService();

    private final OrderStatusDao orderStatusDao = OrderStatusDao.getInstance();

    public OrderStatusDto findById(Integer statusId) {
        Optional<OrderStatus> orderStatus = orderStatusDao.findById(statusId);
        OrderStatusDto orderStatusDto = OrderStatusDto.builder()
                .id(orderStatus.get().getId())
                .orderStatusEnum(orderStatus.get().getOrderStatusEnum())
                .build();
        return orderStatusDto;
    }

    public OrderStatusDto findByStatus(String status) {
        OrderStatus orderStatus = orderStatusDao.findByStatus(status);
        return getOrderStatusDto(orderStatus);
    }

    private static OrderStatusDto getOrderStatusDto(OrderStatus orderStatus) {
        return OrderStatusDto.builder()
                .id(orderStatus.getId())
                .orderStatusEnum(orderStatus.getOrderStatusEnum())
                .build();
    }

    public List<OrderStatusDto> findAll() {
        return orderStatusDao.findAll().stream()
                .map(OrderStatusService::getOrderStatusDto)
                .collect(toList());
    }

    public static OrderStatusService getInstance() {
        return INSTANCE;
    }
}