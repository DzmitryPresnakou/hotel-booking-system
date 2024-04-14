package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.RoomClass;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoomClassDao implements Dao<Long, RoomClass> {

    public static final RoomClassDao INSTANCE = new RoomClassDao();

    public static final String FIND_BY_ID_SQL = """
            SELECT id,
            class,
            price_per_day
            FROM room_class
            WHERE id = ?
            """;

    private RoomClassDao() {
    }

    public static RoomClassDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public RoomClass save(RoomClass roomClass) {
        return null;
    }

    @Override
    public void update(RoomClass roomClass) {

    }

    @Override
    public Optional<RoomClass> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("RoomClass with id %s not found", id), throwables);
        }
    }

    public Optional<RoomClass> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            RoomClass roomClass = null;
            if (resultSet.next()) {
                roomClass = new RoomClass(
                        resultSet.getLong("id"),
                        resultSet.getString("class"),
                        resultSet.getBigDecimal("price_per_day")
                );
            }
            return Optional.ofNullable(roomClass);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("RoomClass with id %s not found", id), throwables);
        }
    }

    @Override
    public List<RoomClass> findAll() {
        return null;
    }
}