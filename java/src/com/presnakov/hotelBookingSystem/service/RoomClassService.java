package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.RoomClassDao;
import com.presnakov.hotelBookingSystem.dto.room.RoomClassDto;
import com.presnakov.hotelBookingSystem.entity.RoomClass;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomClassService {

    private static RoomClassService INSTANCE = new RoomClassService();

    private final RoomClassDao roomClassDao = RoomClassDao.getInstance();

    public RoomClassDto findById(Integer classId) {
        Optional<RoomClass> roomClass = roomClassDao.findById(classId);
        return RoomClassDto.builder()
                    .id(roomClass.get().getId())
                    .comfortClass(roomClass.get().getComfortClass())
                    .pricePerDay(roomClass.get().getPricePerDay())
                    .build();
    }

    public RoomClassDto findByClass(String comfortClass) {
        Optional<RoomClass> roomClass = roomClassDao.findByClass(comfortClass);
        return RoomClassDto.builder()
                .id(roomClass.get().getId())
                .comfortClass(roomClass.get().getComfortClass())
                .pricePerDay(roomClass.get().getPricePerDay())
                .build();
    }

    public List<RoomClassDto> findAll() {
        return roomClassDao.findAll().stream()
                .map(RoomClassService::getRoomClassDto)
                .collect(toList());
    }

    private static RoomClassDto getRoomClassDto(RoomClass roomClass) {
        return RoomClassDto.builder()
                .id(roomClass.getId())
                .comfortClass(roomClass.getComfortClass())
                .pricePerDay(roomClass.getPricePerDay())
                .build();
    }

    public static RoomClassService getInstance() {
        return INSTANCE;
    }
}