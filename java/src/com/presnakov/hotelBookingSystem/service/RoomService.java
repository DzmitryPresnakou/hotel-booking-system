package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.RoomDao;
import com.presnakov.hotelBookingSystem.dto.room.RoomDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomService {

    private static final RoomService INSTANCE = new RoomService();

    private final RoomDao roomDao = RoomDao.getInstance();

    public List<RoomDto> findAll() {
        return roomDao.findAll().stream()
                .map(room -> RoomDto.builder()
                        .id(room.getId())
                        .description(
                                """
                                        beds: %s - class: %s - status: %s - hotel: %s
                                        """.formatted(room.getRoomOccupancy(), room.getRoomClass().getComfortClass(),
                                        room.getRoomStatus().getRoomStatusEnum(), room.getHotel().getName()))
                        .build())
                .collect(toList());
    }

    public static RoomService getInstance() {
        return INSTANCE;
    }
}