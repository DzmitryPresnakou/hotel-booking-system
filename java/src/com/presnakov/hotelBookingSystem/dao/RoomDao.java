package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.entity.Room;
import com.presnakov.hotelBookingSystem.exception.DaoException;
import com.presnakov.hotelBookingSystem.util.ConnectionManager;

import java.sql.*;

public class RoomDao {

    public static final RoomDao INSTANCE = new RoomDao();
    public static final String DELETE_SQL = """
            DELETE FROM room
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO room (room_occupancy, room_class_id, room_status_id, hotel_id)
            VALUES (?, ?, ?, ?);    
            """;

    private RoomDao() {
    }

    public Room save(Room room) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, room.getRoomOccupancy());
            preparedStatement.setLong(2, room.getRoomClassId());
            preparedStatement.setLong(3, room.getRoomStatusId());
            preparedStatement.setLong(4, room.getHotelId());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                room.setId(generatedKeys.getLong("id"));
            }
            return room;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);

            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static RoomDao getInstance() {
        return INSTANCE;
    }
}