package com.presnakov.hotelBookingSystem.entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class RoomOrder {

    private Long id;
    private User user;
    private Room room;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    public RoomOrder(Long id, User user, Room room, OrderStatus orderStatus, PaymentStatus paymentStatus, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public RoomOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomOrder roomOrder = (RoomOrder) o;
        return Objects.equals(id, roomOrder.id) && Objects.equals(user, roomOrder.user) && Objects.equals(room, roomOrder.room) && Objects.equals(orderStatus, roomOrder.orderStatus) && Objects.equals(paymentStatus, roomOrder.paymentStatus) && Objects.equals(checkInDate, roomOrder.checkInDate) && Objects.equals(checkOutDate, roomOrder.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, room, orderStatus, paymentStatus, checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        return "RoomOrder{" +
               "id=" + id +
               ", user=" + user +
               ", room=" + room +
               ", orderStatus=" + orderStatus +
               ", paymentStatus=" + paymentStatus +
               ", checkInDate=" + checkInDate +
               ", checkOutDate=" + checkOutDate +
               '}';
    }
}