package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.RoomOrderDao;
import com.presnakov.hotelBookingSystem.dto.RoomOrderDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class RoomOrderService {

    private static RoomOrderService INSTANCE = new RoomOrderService();

    private final RoomOrderDao roomOrderDao = RoomOrderDao.getInstance();

    private RoomOrderService() {
    }

    public List<RoomOrderDto> findAllByRoomId(Long roomId) {
        return roomOrderDao.findAllByRoomId(roomId).stream()
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