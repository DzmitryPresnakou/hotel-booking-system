package com.presnakov.hotelBookingSystem.entity;

import java.util.Objects;

public class Room {

    private Long id;
    private Long roomOccupancy;
    private Long roomClassId;
    private Long roomStatusId;
    private Long hotelId;

    public Room(Long id, Long roomOccupancy, Long roomClassId, Long roomStatusId, Long hotelId) {
        this.id = id;
        this.roomOccupancy = roomOccupancy;
        this.roomClassId = roomClassId;
        this.roomStatusId = roomStatusId;
        this.hotelId = hotelId;
    }

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomOccupancy() {
        return roomOccupancy;
    }

    public void setRoomOccupancy(Long roomOccupancy) {
        this.roomOccupancy = roomOccupancy;
    }

    public Long getRoomClassId() {
        return roomClassId;
    }

    public void setRoomClassId(Long roomClassId) {
        this.roomClassId = roomClassId;
    }

    public Long getRoomStatusId() {
        return roomStatusId;
    }

    public void setRoomStatusId(Long roomStatusId) {
        this.roomStatusId = roomStatusId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(roomOccupancy, room.roomOccupancy) && Objects.equals(roomClassId, room.roomClassId) && Objects.equals(roomStatusId, room.roomStatusId) && Objects.equals(hotelId, room.hotelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomOccupancy, roomClassId, roomStatusId, hotelId);
    }

    @Override
    public String toString() {
        return "Room{" +
               "id=" + id +
               ", roomOccupancy=" + roomOccupancy +
               ", roomClassId=" + roomClassId +
               ", roomStatusId=" + roomStatusId +
               ", hotelId=" + hotelId +
               '}';
    }
}