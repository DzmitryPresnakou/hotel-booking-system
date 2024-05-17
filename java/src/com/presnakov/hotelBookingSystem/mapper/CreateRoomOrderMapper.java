package com.presnakov.hotelBookingSystem.mapper;

import com.presnakov.hotelBookingSystem.dto.order.CreateOrderDto;
import com.presnakov.hotelBookingSystem.dto.room.CreateRoomDto;
import com.presnakov.hotelBookingSystem.entity.*;
import com.presnakov.hotelBookingSystem.service.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRoomOrderMapper implements Mapper<CreateOrderDto, RoomOrder> {

    private final RoomClassService roomClassService = RoomClassService.getInstance();
    private final RoomStatusService roomStatusService = RoomStatusService.getInstance();
    private final HotelService hotelervice = HotelService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final UserRoleService userRoleService = UserRoleService.getInstance();
    private final boolean IS_USER_ACTIVE = true;

    private static final CreateRoomOrderMapper INSTANCE = new CreateRoomOrderMapper();

    @Override
    public RoomOrder mapFrom(CreateOrderDto object) {
        return RoomOrder.builder()
                .id(object.getId())
                .user(User.builder()
                        .id(object.getUser())
                        .firstName(userService.getUser(object.getUser()).getFirstName())
                        .lastName(userService.getUser(object.getUser()).getLastName())
                        .email(userService.getUser(object.getUser()).getEmail())
                        .password(userService.getUser(object.getUser()).getPassword())
                        .userRole(UserRole.builder()
                                .id(userRoleService.getUserRoleId())
                                .userRoleEnum()
                                .build())
                        .isActive(IS_USER_ACTIVE)
                        .build())



        private Integer id;
        private User user;
        private Room room;
        private OrderStatus orderStatus;
        private PaymentStatus paymentStatus;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;


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

    public static CreateRoomOrderMapper getInstance() {
        return INSTANCE;
    }
}