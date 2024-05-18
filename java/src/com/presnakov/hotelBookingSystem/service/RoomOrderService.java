package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.RoomOrderDao;
import com.presnakov.hotelBookingSystem.dto.order.CreateOrderDto;
import com.presnakov.hotelBookingSystem.dto.order.OrderStatusDto;
import com.presnakov.hotelBookingSystem.dto.order.PaymentStatusDto;
import com.presnakov.hotelBookingSystem.dto.order.RoomOrderCompleteDto;
import com.presnakov.hotelBookingSystem.dto.user.UserDto;
import com.presnakov.hotelBookingSystem.entity.RoomOrder;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.mapper.CreateRoomOrderMapper;
import com.presnakov.hotelBookingSystem.validator.CreateRoomOrderValidator;
import com.presnakov.hotelBookingSystem.validator.ValidationResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomOrderService {

    private static RoomOrderService INSTANCE = new RoomOrderService();

    private final RoomOrderDao roomOrderDao = RoomOrderDao.getInstance();
    private final RoomService roomService = RoomService.getInstance();
    private final CreateRoomOrderValidator createRoomOrderValidator = CreateRoomOrderValidator.getInstance();
    private final CreateRoomOrderMapper createRoomOrderMapper = CreateRoomOrderMapper.getInstance();

    public List<RoomOrderCompleteDto> findAll() {
        return roomOrderDao.findAll().stream()
                .map(roomOrder -> RoomOrderCompleteDto.builder()
                        .id(roomOrder.getId())
                        .userDto(getUserDto(roomOrder))
                        .roomCompleteDto(roomService.getRoom(roomOrder.getRoom().getId()))
                        .orderStatusDto(getOrderStatusDto(roomOrder))
                        .paymentStatusDto(getPaymentStatusDto(roomOrder))
                        .checkInDate(roomOrder.getCheckInDate())
                        .checkOutDate(roomOrder.getCheckOutDate())
                        .build())
                .collect(toList());
    }

    public Integer create(CreateOrderDto createOrderDto) {
        ValidationResult validationResult = createRoomOrderValidator.isValid(createOrderDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        RoomOrder roomOrderEntity = createRoomOrderMapper.mapFrom(createOrderDto);
        roomOrderDao.save(roomOrderEntity);
        return roomOrderEntity.getId();
    }

    private static OrderStatusDto getOrderStatusDto(RoomOrder roomOrder) {
        return OrderStatusDto.builder()
                .id(roomOrder.getOrderStatus().getId())
                .orderStatusEnum(roomOrder.getOrderStatus().getOrderStatusEnum())
                .build();
    }

    private static PaymentStatusDto getPaymentStatusDto(RoomOrder roomOrder) {
        return PaymentStatusDto.builder()
                .id(roomOrder.getPaymentStatus().getId())
                .paymentStatusEnum(roomOrder.getPaymentStatus().getPaymentStatusEnum())
                .build();
    }

    private static UserDto getUserDto(RoomOrder roomOrder) {
        return UserDto.builder()
                .id(roomOrder.getUser().getId())
                .email(roomOrder.getUser().getEmail())
                .build();
    }

    public static RoomOrderService getInstance() {
        return INSTANCE;
    }
}