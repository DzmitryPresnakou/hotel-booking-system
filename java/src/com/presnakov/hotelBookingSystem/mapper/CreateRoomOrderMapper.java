package com.presnakov.hotelBookingSystem.mapper;

import com.presnakov.hotelBookingSystem.datasourse.LocalDateFormatter;
import com.presnakov.hotelBookingSystem.dto.order.CreateOrderDto;
import com.presnakov.hotelBookingSystem.entity.*;
import com.presnakov.hotelBookingSystem.service.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRoomOrderMapper implements Mapper<CreateOrderDto, RoomOrder> {

    private final OrderStatusService orderStatusService = OrderStatusService.getInstance();
    private final PaymentStatusService paymentStatusService = PaymentStatusService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final RoomService roomService = RoomService.getInstance();

    private final UserRoleService userRoleService = UserRoleService.getInstance();
    private final boolean IS_USER_ACTIVE = true;

    private static final CreateRoomOrderMapper INSTANCE = new CreateRoomOrderMapper();

    @Override
    public RoomOrder mapFrom(CreateOrderDto object) {
        return RoomOrder.builder()
                .id(object.getId())
                .user(getUser(object))
                .room(getRoom(object))
                .orderStatus(getOrderStatus(object))
                .paymentStatus(getPaymentStatus(object))
                .checkInDate(LocalDateFormatter.format(object.getCheckInDate()))
                .checkOutDate(LocalDateFormatter.format(object.getCheckOutDate()))
                .build();
    }

    private PaymentStatus getPaymentStatus(CreateOrderDto object) {
        return PaymentStatus.builder()
                .id(object.getPaymentStatus())
                .paymentStatusEnum(paymentStatusService.findById(object.getPaymentStatus()).getPaymentStatusEnum())
                .build();
    }

    private OrderStatus getOrderStatus(CreateOrderDto object) {
        return OrderStatus.builder()
                .id(object.getOrderStatus())
                .orderStatusEnum(orderStatusService.findById(object.getOrderStatus()).getOrderStatusEnum())
                .build();
    }

    private Room getRoom(CreateOrderDto object) {
        return Room.builder()
                .id(object.getRoom())
                .roomOccupancy(roomService.getRoom(object.getRoom()).getOccupancy())
                .roomClass(getRoomClass(object))
                .roomStatus(getRoomStatus(object))
                .hotel(getHotel(object))
                .build();
    }

    private Hotel getHotel(CreateOrderDto object) {
        return Hotel.builder()
                .id(roomService.getRoom(object.getRoom()).getHotelDto().getId())
                .name(roomService.getRoom(object.getRoom()).getHotelDto().getName())
                .build();
    }

    private RoomStatus getRoomStatus(CreateOrderDto object) {
        return RoomStatus.builder()
                .id(roomService.getRoom(object.getRoom()).getRoomStatusDto().getId())
                .roomStatusEnum(roomService.getRoom(object.getRoom()).getRoomStatusDto().getRoomStatusEnum())
                .build();
    }

    private RoomClass getRoomClass(CreateOrderDto object) {
        return RoomClass.builder()
                .id(roomService.getRoom(object.getRoom()).getRoomClassDto().getId())
                .comfortClass(roomService.getRoom(object.getRoom()).getRoomClassDto().getComfortClass())
                .pricePerDay(roomService.getRoom(object.getRoom()).getRoomClassDto().getPricePerDay())
                .build();
    }

    private User getUser(CreateOrderDto object) {
        return User.builder()
                .id(object.getUser())
                .firstName(userService.getUser(object.getUser()).getFirstName())
                .lastName(userService.getUser(object.getUser()).getLastName())
                .email(userService.getUser(object.getUser()).getEmail())
                .password(userService.getUser(object.getUser()).getPassword())
                .userRole(getUserRole(object))
                .isActive(IS_USER_ACTIVE)
                .build();
    }

    private UserRole getUserRole(CreateOrderDto object) {
        return UserRole.builder()
                .id(userRoleService.getUserRoleId(userService.getUser(object.getUser()).getUserRoleDto().getUserRoleEnum()))
                .userRoleEnum(userService.getUser(object.getUser()).getUserRoleDto().getUserRoleEnum())
                .build();
    }

    public static CreateRoomOrderMapper getInstance() {
        return INSTANCE;
    }
}