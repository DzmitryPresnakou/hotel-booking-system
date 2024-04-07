package com.presnakov.hotelBookingSystem;

import com.presnakov.hotelBookingSystem.dao.RoomDao;
import com.presnakov.hotelBookingSystem.entity.Room;

import java.util.Optional;

public class DaoRunner {

    public static void main(String[] args) {
        var rooms = RoomDao.getInstance().findAll();
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
        room.setRoomOccupancy(2L);
        room.setRoomClassId(1L);
        room.setRoomStatusId(1L);
        room.setHotelId(1L);
        var savedRoom = roomDao.save(room);
        System.out.println(savedRoom);
    }
}
