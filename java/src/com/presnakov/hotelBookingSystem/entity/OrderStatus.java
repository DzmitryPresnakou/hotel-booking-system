package com.presnakov.hotelBookingSystem.entity;

import java.util.Objects;

public class OrderStatus {

    private Long id;
    private OrderStatusEnum orderStatusEnum;

    public OrderStatus(Long id, OrderStatusEnum orderStatusEnum) {
        this.id = id;
        this.orderStatusEnum = orderStatusEnum;
    }

    public OrderStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatusEnum getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return Objects.equals(id, that.id) && orderStatusEnum == that.orderStatusEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderStatusEnum);
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
               "id=" + id +
               ", orderStatusEnum=" + orderStatusEnum +
               '}';
    }
}