package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.RoomDao;
import com.presnakov.hotelBookingSystem.dto.hotel.HotelDto;
import com.presnakov.hotelBookingSystem.dto.room.CreateRoomDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomClassDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomCompleteDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomStatusDto;
import com.presnakov.hotelBookingSystem.entity.Room;
import com.presnakov.hotelBookingSystem.mapper.CreateRoomMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomService {

    private static final RoomService INSTANCE = new RoomService();

    private final RoomDao roomDao = RoomDao.getInstance();
    private final CreateRoomMapper createRoomMapper = CreateRoomMapper.getInstance();

    public List<RoomCompleteDto> findAll() {
        return roomDao.findAll().stream()
                .map(RoomService::getRoomCompleteDto)
                .collect(toList());
    }

    public Integer create(CreateRoomDto createRoomDto) {
        Room roomEntity = createRoomMapper.mapFrom(createRoomDto);
        roomDao.save(roomEntity);
        return roomEntity.getId();
    }

    private static RoomCompleteDto getRoomCompleteDto(Room room) {
        return RoomCompleteDto.builder()
                .id(room.getId())
                .occupancy(room.getRoomOccupancy())
                .roomClassDto(getRoomClassDto(room))
                .roomStatusDto(getRoomStatusDto(room))
                .hotelDto(getHotelDto(room))
                .build();
    }

    private static RoomClassDto getRoomClassDto(Room room) {
        return RoomClassDto.builder()
                .id(room.getRoomClass().getId())
                .comfortClass(room.getRoomClass().getComfortClass())
                .build();
    }

    private static RoomStatusDto getRoomStatusDto(Room room) {
        return RoomStatusDto.builder()
                .id(room.getRoomStatus().getId())
                .roomStatusEnum(room.getRoomStatus().getRoomStatusEnum())
                .build();
    }

    private static HotelDto getHotelDto(Room room) {
        return HotelDto.builder()
                .id(room.getHotel().getId())
                .name(room.getHotel().getName())
                .build();
    }

    public static RoomService getInstance() {
        return INSTANCE;
    }
}