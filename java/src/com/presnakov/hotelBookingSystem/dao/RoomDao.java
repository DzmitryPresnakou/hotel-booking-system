package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.dto.RoomFilter;
import com.presnakov.hotelBookingSystem.entity.Room;
import com.presnakov.hotelBookingSystem.exception.DaoException;
import com.presnakov.hotelBookingSystem.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

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
    public static final String UPDATE_SQL = """
            UPDATE room
            SET room_occupancy = ?,
                room_class_id = ?,
                room_status_id = ?,
                hotel_id = ?
             WHERE id = ?          
            """;
    public static final String FIND_ALL_SQL = """
            SELECT id,
            room_occupancy,
            room_class_id,
            room_status_id,
            hotel_id
            FROM room
            """;
    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private RoomDao() {
    }

    public List<Room> findAll(RoomFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.roomOccupancy() != null) {
            whereSql.add("room_occupancy = ?");
            parameters.add(filter.roomOccupancy());
        }
        if (filter.roomClassId() != null) {
            whereSql.add("room_class_id = ?");
            parameters.add(filter.roomClassId());
        }
        if (filter.roomStatusId() != null) {
            whereSql.add("room_status_id = ?");
            parameters.add(filter.roomStatusId());
        }
        if (filter.hotelId() != null) {
            whereSql.add("hotel_id = ?");
            parameters.add(filter.hotelId());
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        var where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));
        var sql = FIND_ALL_SQL + where;

        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            var resultSet = preparedStatement.executeQuery();
            List<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                rooms.add(buildRoom(resultSet));
            }
            return rooms;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<Room> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                rooms.add(buildRoom(resultSet));
            }
            return rooms;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<Room> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Room room = null;
            if (resultSet.next()) {
                room = buildRoom(resultSet);
            }
            return Optional.ofNullable(room);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static Room buildRoom(ResultSet resultSet) throws SQLException {
        return new Room(
                resultSet.getLong("id"),
                resultSet.getLong("room_occupancy"),
                resultSet.getLong("room_class_id"),
                resultSet.getLong("room_status_id"),
                resultSet.getLong("hotel_id")
        );
    }

    public void update(Room room) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, room.getRoomOccupancy());
            preparedStatement.setLong(2, room.getRoomClassId());
            preparedStatement.setLong(3, room.getRoomStatusId());
            preparedStatement.setLong(4, room.getHotelId());
            preparedStatement.setLong(5, room.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
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
            if (generatedKeys.next()) {
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