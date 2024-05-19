package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.RoomDao;
import com.presnakov.hotelBookingSystem.dto.hotel.HotelDto;
import com.presnakov.hotelBookingSystem.dto.room.CreateRoomDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomClassDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomCompleteDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomStatusDto;
import com.presnakov.hotelBookingSystem.entity.Room;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.mapper.CreateRoomMapper;
import com.presnakov.hotelBookingSystem.validator.CreateRoomValidator;
import com.presnakov.hotelBookingSystem.validator.ValidationResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomService {

    private static final RoomService INSTANCE = new RoomService();

    private final RoomDao roomDao = RoomDao.getInstance();
    private final CreateRoomValidator createRoomValidator = CreateRoomValidator.getInstance();
    private final CreateRoomMapper createRoomMapper = CreateRoomMapper.getInstance();

    public List<RoomCompleteDto> findAll() {
        return roomDao.findAll().stream()
                .map(RoomService::getRoomCompleteDto)
                .collect(toList());
    }

    public RoomCompleteDto getRoom(Integer id) {
        Optional<Room> room = roomDao.findById(id);
        return room.map(RoomService::getRoomCompleteDto).orElse(null);
    }

    public Integer create(CreateRoomDto createRoomDto) {
        ValidationResult validationResult = createRoomValidator.isValid(createRoomDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        Room roomEntity = createRoomMapper.mapFrom(createRoomDto);
        roomDao.save(roomEntity);
        return roomEntity.getId();
    }

    public Integer update(CreateRoomDto createRoomDto) {
        Room roomEntity = createRoomMapper.mapFrom(createRoomDto);
        roomDao.update(roomEntity);
        return roomEntity.getId();
    }

    public boolean deleteRoom(Integer id) {
        if (id < 0) {
            return false;
        }
        return roomDao.delete(id);
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
                .pricePerDay(room.getRoomClass().getPricePerDay())
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