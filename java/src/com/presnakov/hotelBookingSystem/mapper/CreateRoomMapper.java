package com.presnakov.hotelBookingSystem.mapper;

import com.presnakov.hotelBookingSystem.dto.room.CreateRoomDto;
import com.presnakov.hotelBookingSystem.entity.Hotel;
import com.presnakov.hotelBookingSystem.entity.Room;
import com.presnakov.hotelBookingSystem.entity.RoomClass;
import com.presnakov.hotelBookingSystem.entity.RoomStatus;
import com.presnakov.hotelBookingSystem.service.HotelService;
import com.presnakov.hotelBookingSystem.service.RoomClassService;
import com.presnakov.hotelBookingSystem.service.RoomStatusService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRoomMapper implements Mapper<CreateRoomDto, Room> {

    private final RoomClassService roomClassService = RoomClassService.getInstance();
    private final RoomStatusService roomStatusService = RoomStatusService.getInstance();
    private final HotelService hotelervice = HotelService.getInstance();

    private static final CreateRoomMapper INSTANCE = new CreateRoomMapper();

    @Override
    public Room mapFrom(CreateRoomDto object) {
        return Room.builder()
                .id(object.getId())
                .roomOccupancy(object.getOccupancy())
                .roomClass(RoomClass.builder()
                        .id(object.getRoomClassDto())
                        .comfortClass(roomClassService.findById(object.getRoomClassDto()).getComfortClass())
                        .pricePerDay(roomClassService.findById(object.getRoomClassDto()).getPricePerDay())
                        .build())
                .roomStatus(RoomStatus.builder()
                        .id(object.getRoomStatusDto())
                        .roomStatusEnum(roomStatusService.findById(object.getRoomStatusDto()).getRoomStatusEnum())
                        .build())
                .hotel(Hotel.builder()
                        .id(object.getHotelDto())
                        .name(hotelervice.findById(object.getHotelDto()).getName())
                        .build())
                .build();
    }

    public static CreateRoomMapper getInstance() {
        return INSTANCE;
    }
}