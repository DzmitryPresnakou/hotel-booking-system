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
import java.util.Optional;

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

    public RoomOrderCompleteDto findById(Integer roomOrderId) {
        Optional<RoomOrder> roomOrder = roomOrderDao.findById(roomOrderId);
        return RoomOrderCompleteDto.builder()
                .id(roomOrder.get().getId())
                .userDto(getUserDto(roomOrder.get()))
                .roomCompleteDto(roomService.getRoom(roomOrder.get().getRoom().getId()))
                .orderStatusDto(getOrderStatusDto(roomOrder.get()))
                .paymentStatusDto(getPaymentStatusDto(roomOrder.get()))
                .checkInDate(roomOrder.get().getCheckInDate())
                .checkOutDate(roomOrder.get().getCheckOutDate())
                .build();
    }

    public RoomOrderCompleteDto findByRoomId(Integer roomOrderId) {
        Optional<RoomOrder> roomOrder = roomOrderDao.findById(roomOrderId);
        return RoomOrderCompleteDto.builder()
                .id(roomOrder.get().getId())
                .userDto(getUserDto(roomOrder.get()))
                .roomCompleteDto(roomService.getRoom(roomOrder.get().getRoom().getId()))
                .orderStatusDto(getOrderStatusDto(roomOrder.get()))
                .paymentStatusDto(getPaymentStatusDto(roomOrder.get()))
                .checkInDate(roomOrder.get().getCheckInDate())
                .checkOutDate(roomOrder.get().getCheckOutDate())
                .build();
    }

    public List<RoomOrderCompleteDto> findAllByUserId(Integer userId) {
        return roomOrderDao.findAllByUserId(userId).stream()
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