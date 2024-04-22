package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.RoomStatus;
import com.presnakov.hotelBookingSystem.entity.RoomStatusEnum;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomStatusDao implements Dao<Long, RoomStatus> {

    public static final RoomStatusDao INSTANCE = new RoomStatusDao();

    private static final String DELETE_SQL = """
            DELETE FROM room_status
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO room_status (room_status)
            VALUES (?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE room_status
            SET room_status = ?
            WHERE id = ?;
             """;

    public static final String FIND_ALL_SQL = """
            SELECT id,
            room_status,
            FROM room_status
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private RoomStatusDao() {
    }

    public static RoomStatusDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public RoomStatus save(RoomStatus roomStatus) {
        return null;
    }

    @Override
    public void update(RoomStatus roomStatus) {

    }

    @Override
    public Optional<RoomStatus> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room Status with id %s not found", id), throwables);
        }
    }

    public Optional<RoomStatus> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            RoomStatus roomStatus = null;
            if (resultSet.next()) {
                roomStatus = buildRoomStatus(resultSet);
            }
            return Optional.ofNullable(roomStatus);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room Status with id %s not found", id), throwables);
        }
    }

    @Override
    public List<RoomStatus> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<RoomStatus> roomStatuses = new ArrayList<>();
            while (resultSet.next()) {
                roomStatuses.add(buildRoomStatus(resultSet));
            }
            return roomStatuses;
        } catch (SQLException throwables) {
            throw new DaoException("Room classes not found", throwables);
        }
    }

    private RoomStatus buildRoomStatus(ResultSet resultSet) throws SQLException {
        return new RoomStatus(
                resultSet.getLong("id"),
                RoomStatusEnum.valueOf(resultSet.getObject("room_status", String.class))
        );
    }
}