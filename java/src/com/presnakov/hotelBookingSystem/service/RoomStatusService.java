package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.RoomStatusDao;
import com.presnakov.hotelBookingSystem.dto.room.RoomStatusDto;
import com.presnakov.hotelBookingSystem.entity.RoomStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomStatusService {

    private static RoomStatusService INSTANCE = new RoomStatusService();

    private final RoomStatusDao roomStatusDao = RoomStatusDao.getInstance();

    public RoomStatusDto findById(Integer statusId) {
        Optional<RoomStatus> roomStatus = roomStatusDao.findById(statusId);
        return RoomStatusDto.builder()
                .id(roomStatus.get().getId())
                .roomStatusEnum(roomStatus.get().getRoomStatusEnum())
                .build();
    }

    public RoomStatusDto findByStatus(String status) {
        Optional<RoomStatus> roomStatus = roomStatusDao.findByStatus(status);
        return RoomStatusDto.builder()
                .id(roomStatus.get().getId())
                .roomStatusEnum(roomStatus.get().getRoomStatusEnum())
                .build();
    }

    public List<RoomStatusDto> findAll() {
        return roomStatusDao.findAll().stream()
                .map(RoomStatusService::getRoomStatusDto)
                .collect(toList());
    }

    private static RoomStatusDto getRoomStatusDto(RoomStatus roomStatus) {
        return RoomStatusDto.builder()
                .id(roomStatus.getId())
                .roomStatusEnum(roomStatus.getRoomStatusEnum())
                .build();
    }

    public static RoomStatusService getInstance() {
        return INSTANCE;
    }
}