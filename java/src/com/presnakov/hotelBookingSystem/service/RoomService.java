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
                                %s - %s - %s - %s
                                 """.formatted(room.getRoomOccupancy(), room.getRoomClass().getId(),
                                room.getRoomStatus().getId(), room.getHotel().getId())
                ))
                .collect(toList());
    }

    public static RoomService getInstance() {
        return INSTANCE;
    }
}