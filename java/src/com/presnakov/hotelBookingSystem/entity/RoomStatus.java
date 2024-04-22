package com.presnakov.hotelBookingSystem.entity;

import java.util.Objects;

public class RoomStatus {

    private Long id;
    private RoomStatusEnum roomStatusEnum;

    public RoomStatus(Long id, RoomStatusEnum roomStatusEnum) {
        this.id = id;
        this.roomStatusEnum = roomStatusEnum;
    }

    public RoomStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomStatusEnum getRoomStatusEnum() {
        return roomStatusEnum;
    }

    public void setRoomStatusEnum(RoomStatusEnum roomStatusEnum) {
        this.roomStatusEnum = roomStatusEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomStatus that = (RoomStatus) o;
        return Objects.equals(id, that.id) && roomStatusEnum == that.roomStatusEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomStatusEnum);
    }

    @Override
    public String toString() {
        return "RoomStatus{" +
               "id=" + id +
               ", roomStatusEnum=" + roomStatusEnum +
               '}';
    }
}
