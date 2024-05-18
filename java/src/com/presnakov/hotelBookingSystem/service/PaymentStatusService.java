package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.PaymentStatusDao;
import com.presnakov.hotelBookingSystem.dto.order.PaymentStatusDto;
import com.presnakov.hotelBookingSystem.entity.PaymentStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentStatusService {

    private static PaymentStatusService INSTANCE = new PaymentStatusService();

    private final PaymentStatusDao paymentStatusDao = PaymentStatusDao.getInstance();

    public PaymentStatusDto findById(Integer paymentId) {
        Optional<PaymentStatus> paymentStatus = paymentStatusDao.findById(paymentId);
        return PaymentStatusDto.builder()
                .id(paymentStatus.get().getId())
                .paymentStatusEnum(paymentStatus.get().getPaymentStatusEnum())
                .build();
    }

    public PaymentStatusDto findByStatus(String status) {
        PaymentStatus paymentStatus = paymentStatusDao.findByStatus(status);
        return getPaymentStatusDto(paymentStatus);
    }

    private static PaymentStatusDto getPaymentStatusDto(PaymentStatus paymentStatus) {
        return PaymentStatusDto.builder()
                .id(paymentStatus.getId())
                .paymentStatusEnum(paymentStatus.getPaymentStatusEnum())
                .build();
    }

    public List<PaymentStatusDto> findAll() {
        return paymentStatusDao.findAll().stream()
                .map(PaymentStatusService::getPaymentStatusDto)
                .collect(toList());
    }

    public static PaymentStatusService getInstance() {
        return INSTANCE;
    }
}