package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.dto.room.RoomFilter;
import com.presnakov.hotelBookingSystem.entity.Room;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class RoomDao implements Dao<Integer, Room> {

    private static final RoomDao INSTANCE = new RoomDao();

    private static final String DELETE_SQL = """
            DELETE FROM room
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO room (room_occupancy, room_class_id, room_status_id, hotel_id)
            VALUES (?, ?, ?, ?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE room
            SET room_occupancy = ?,
                room_class_id = ?,
                room_status_id = ?,
                hotel_id = ?
             WHERE id = ?;
             """;
    private static final String FIND_ALL_SQL = """
            SELECT id,
                   room_occupancy,
                   room_class_id,
                   room_status_id,
                   hotel_id
            FROM room
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE room.id = ?
            """;
    private static final String ID = "id";
    private static final String ROOM_OCCUPANCY = "room_occupancy";
    private static final String ROOM_CLASS_ID = "room_class_id";
    private static final String ROOM_STATUS_ID = "room_status_id";
    private static final String HOTEL_ID = "hotel_id";

    private final RoomClassDao roomClassDao = RoomClassDao.getInstance();
    private final RoomStatusDao roomStatusDao = RoomStatusDao.getInstance();
    private final HotelDao hotelDao = HotelDao.getInstance();

    private RoomDao() {
    }

    public static RoomDao getInstance() {
        return INSTANCE;
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
            throw new DaoException("Rooms not found", throwables);
        }
    }

    public List<Room> findAll(RoomFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.roomOccupancy() != null) {
            whereSql.add("room_occupancy = ?");
            parameters.add(filter.roomOccupancy());
        }
        if (filter.roomClass().getId() != null) {
            whereSql.add("room_class_id = ?");
            parameters.add(filter.roomClass().getId());
        }
        if (filter.roomStatus().getId() != null) {
            whereSql.add("room_status_id = ?");
            parameters.add(filter.roomStatus().getId());
        }
        if (filter.hotelId() != null) {
            whereSql.add("hotel_id = ?");
            parameters.add(filter.hotelId());
        }
        var where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));

        var sql = FIND_ALL_SQL + (where.isBlank() ? "" : where);

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
            throw new DaoException(String.format("Room with id %s not found", "id"), throwables);
        }
    }

    @Override
    public Optional<Room> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room Role with id %s not found", id), throwables);
        }
    }

    public Optional<Room> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            Room room = null;
            if (resultSet.next()) {
                room = buildRoom(resultSet);
            }
            return Optional.ofNullable(room);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room with id %s not found", id), throwables);
        }
    }

    private Room buildRoom(ResultSet resultSet) throws SQLException {
        return new Room(
                resultSet.getInt(ID),
                resultSet.getInt(ROOM_OCCUPANCY),
                roomClassDao.findById(resultSet.getInt(ROOM_CLASS_ID),
                        resultSet.getStatement().getConnection()).orElse(null),
                roomStatusDao.findById(resultSet.getInt(ROOM_STATUS_ID),
                        resultSet.getStatement().getConnection()).orElse(null),
                hotelDao.findById(resultSet.getInt(HOTEL_ID),
                        resultSet.getStatement().getConnection()).orElse(null)
        );
    }

    public void update(Room room) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, room.getRoomOccupancy());
            preparedStatement.setInt(2, room.getRoomClass().getId());
            preparedStatement.setInt(3, room.getRoomStatus().getId());
            preparedStatement.setInt(4, room.getHotel().getId());
            preparedStatement.setInt(5, room.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room with id %s not found", room.getId()), throwables.getCause());
        }
    }

    public Room save(Room room) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, room.getRoomOccupancy());
            preparedStatement.setInt(2, room.getRoomClass().getId());
            preparedStatement.setInt(3, room.getRoomStatus().getId());
            preparedStatement.setInt(4, room.getHotel().getId());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                room.setId(generatedKeys.getInt(ID));
            }
            return room;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room with id %s not found", room.getId()), throwables.getCause());
        }
    }

    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setInt(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room with id %s not found", id), throwables);
        }
    }
}