package com.presnakov.hotelBookingSystem.dto;
import java.util.Objects;

public class RoomOrderDto {

    private final Long id;
    private final Long roomId;
    private final Long orderStatusId;

    public RoomOrderDto(Long id, Long roomId, Long orderStatusId) {
        this.id = id;
        this.roomId = roomId;
        this.orderStatusId = orderStatusId;
    }

    public Long getId() {
        return id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomOrderDto that = (RoomOrderDto) o;
        return Objects.equals(id, that.id) && Objects.equals(roomId, that.roomId) && Objects.equals(orderStatusId, that.orderStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, orderStatusId);
    }

    @Override
    public String toString() {
        return "RoomOrderDto{" +
               "id=" + id +
               ", roomId=" + roomId +
               ", orderStatusId=" + orderStatusId +
               '}';
    }
}