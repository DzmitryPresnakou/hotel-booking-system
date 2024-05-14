package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.RoomOrderDao;
import com.presnakov.hotelBookingSystem.dto.order.RoomOrderDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomOrderService {

    private static RoomOrderService INSTANCE = new RoomOrderService();

    private final RoomOrderDao roomOrderDao = RoomOrderDao.getInstance();

    public List<RoomOrderDto> findAllByRoomId(Integer roomId) {
        return roomOrderDao.findAllByRoomId(roomId).stream()
                .map(roomOrder -> RoomOrderDto.builder().id(roomOrder.getId())
                        .roomId(roomOrder.getId())
                        .orderStatusId(roomOrder.getOrderStatus().getId())
                        .build())
                .collect(toList());
    }

    public List<RoomOrderDto> findAll() {
        return roomOrderDao.findAll().stream()
                .map(roomOrder -> RoomOrderDto.builder().id(roomOrder.getId())
                        .roomId(roomOrder.getId())
                        .orderStatusId(roomOrder.getOrderStatus().getId())
                        .build())
                .collect(toList());
    }

    public static RoomOrderService getInstance() {
        return INSTANCE;
    }
}