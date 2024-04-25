package com.presnakov.hotelBookingSystem.entity;

import java.util.Objects;

public class PaymentStatus {

    private Long id;
    private PaymentStatusEnum paymentStatusEnum;

    public PaymentStatus(Long id, PaymentStatusEnum paymentStatusEnum) {
        this.id = id;
        this.paymentStatusEnum = paymentStatusEnum;
    }

    public PaymentStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatusEnum getPaymentStatusEnum() {
        return paymentStatusEnum;
    }

    public void setPaymentStatusEnum(PaymentStatusEnum paymentStatusEnum) {
        this.paymentStatusEnum = paymentStatusEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentStatus that = (PaymentStatus) o;
        return Objects.equals(id, that.id) && paymentStatusEnum == that.paymentStatusEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentStatusEnum);
    }

    @Override
    public String toString() {
        return "PaymentStatus{" +
               "id=" + id +
               ", paymentStatusEnum=" + paymentStatusEnum +
               '}';
    }
}
