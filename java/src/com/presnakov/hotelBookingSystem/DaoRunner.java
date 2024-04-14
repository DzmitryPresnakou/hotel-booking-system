package com.presnakov.hotelBookingSystem;

import com.presnakov.hotelBookingSystem.dao.RoomClassDao;
import com.presnakov.hotelBookingSystem.dao.RoomDao;
import com.presnakov.hotelBookingSystem.dto.RoomFilter;
import com.presnakov.hotelBookingSystem.entity.Room;

import java.util.List;
import java.util.Optional;

public class DaoRunner {

    public static void main(String[] args) {

        findRoomClassById();

        findRoomById();
    }

    private static void findRoomClassById() {
        var roomClass = RoomClassDao.getInstance().findById(1L);
        System.out.println(roomClass);
    }

    private static void findRoomById() {
        var room = RoomDao.getInstance().findById(2L);
        System.out.println(room);
    }

    private static void filterTest() {
        var roomFilter = new RoomFilter(5, 0, 5, null, null, null);
        List<Room> rooms = RoomDao.getInstance().findAll(roomFilter);
        System.out.println(rooms);
    }

    private static void deleteTest() {
        var roomDao = RoomDao.getInstance();
        boolean deleteResult = roomDao.delete(9L);
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
        var roomClass = RoomClassDao.getInstance().findById(1L);
        room.setRoomOccupancy(2L);
        room.setRoomClass(roomClass.get());
        room.setRoomStatusId(1L);
        room.setHotelId(1L);
        var savedRoom = roomDao.save(room);
        System.out.println(savedRoom);
    }
}