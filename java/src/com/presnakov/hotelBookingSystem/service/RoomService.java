package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.RoomDao;
import com.presnakov.hotelBookingSystem.dto.RoomDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class RoomService {

    private static final RoomService INSTANCE = new RoomService();

    private final RoomDao roomDao = RoomDao.getInstance();

    private RoomService() {
    }

    public List<RoomDto> findAll() {
        return roomDao.findAll().stream()
                .map(room -> new RoomDto(
                        room.getId(),
                        """
                                beds: %s - class: %s - status: %s - hotel: %s
                                 """.formatted(room.getRoomOccupancy(), room.getRoomClass().getComfortClass(),
                                room.getRoomStatus().getRoomStatusEnum(), room.getHotel().getName())
                ))
                .collect(toList());
    }

    public static RoomService getInstance() {
        return INSTANCE;
    }
}