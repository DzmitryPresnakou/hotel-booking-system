package com.presnakov.hotelBookingSystem.entity;

import java.util.Objects;

public class Room {

    private Long id;
    private Long roomOccupancy;
    private RoomClass roomClass;
    private RoomStatus roomStatus;
    private Hotel hotel;

    public Room(Long id, Long roomOccupancy, RoomClass roomClass, RoomStatus roomStatus, Hotel hotel) {
        this.id = id;
        this.roomOccupancy = roomOccupancy;
        this.roomClass = roomClass;
        this.roomStatus = roomStatus;
        this.hotel = hotel;
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

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(roomOccupancy, room.roomOccupancy) && Objects.equals(roomClass, room.roomClass) && Objects.equals(roomStatus, room.roomStatus) && Objects.equals(hotel, room.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomOccupancy, roomClass, roomStatus, hotel);
    }

    @Override
    public String toString() {
        return "Room{" +
               "id=" + id +
               ", roomOccupancy=" + roomOccupancy +
               ", roomClass=" + roomClass +
               ", roomStatus=" + roomStatus +
               ", hotel=" + hotel +
               '}';
    }
}