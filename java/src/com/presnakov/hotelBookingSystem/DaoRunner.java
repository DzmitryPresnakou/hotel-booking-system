package com.presnakov.hotelBookingSystem;

import com.presnakov.hotelBookingSystem.dao.RoomDao;
import com.presnakov.hotelBookingSystem.entity.Room;

public class DaoRunner {

    public static void main(String[] args) {

        var roomDao = RoomDao.getInstance();
        boolean deleteResult = roomDao.delete(8L);
        System.out.println(deleteResult);
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
