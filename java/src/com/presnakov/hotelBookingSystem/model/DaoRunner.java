package com.presnakov.hotelBookingSystem.model;

import com.presnakov.hotelBookingSystem.dao.RoomDao;
import com.presnakov.hotelBookingSystem.entity.Room;
import com.presnakov.hotelBookingSystem.entity.RoomClass;

import java.math.BigDecimal;
import java.util.Optional;

public class DaoRunner {

    public static void main(String[] args) {

        var rooms = RoomDao.getInstance().findById(1L);
        System.out.println(rooms);
    }

    private static void deleteTest() {
        var roomDao = RoomDao.getInstance();
        boolean deleteResult = roomDao.delete(8L);
        System.out.println(deleteResult);
    }

    private static void updateTest() {
        var roomDao = RoomDao.getInstance();
        Optional<Room> maybeRoom = roomDao.findById(2L);
        System.out.println(maybeRoom);

        maybeRoom.ifPresent(room -> {
            room.setRoomOccupancy(Long.valueOf(1L));
            roomDao.update(room);
        });
    }

    private static void saveTest() {
        var roomDao = RoomDao.getInstance();
        var room = new Room();
        var roomClass = new RoomClass(10L, "comfort", new BigDecimal(49.9));
        room.setRoomOccupancy(2L);
        room.setRoomClass(roomClass);
        room.setRoomStatusId(1L);
        room.setHotelId(1L);
        var savedRoom = roomDao.save(room);
        System.out.println(savedRoom);
    }
}
