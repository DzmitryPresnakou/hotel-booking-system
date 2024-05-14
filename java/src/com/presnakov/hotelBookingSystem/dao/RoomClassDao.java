package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.RoomClass;
import com.presnakov.hotelBookingSystem.entity.RoomClassEnum;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomClassDao implements Dao<Integer, RoomClass> {

    private static final RoomClassDao INSTANCE = new RoomClassDao();

    private static final String DELETE_SQL = """
            DELETE FROM room_class
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO room_class (class, price_per_day)
            VALUES (?, ?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE room_class
            SET class = ?,
                price_per_day = ?
             WHERE id = ?;
             """;
    private static final String FIND_ALL_SQL = """
            SELECT id,
            class,
            price_per_day
            FROM room_class
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String FIND_BY_CLASS_SQL = FIND_ALL_SQL + """
            WHERE class = ?
            """;
    private static final String ID = "id";
    private static final String PRICE_PER_DAY = "price_per_day";
    private static final String CLASS = "class";

    private RoomClassDao() {
    }

    public static RoomClassDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setInt(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room class with id %s not found", id), throwables);
        }
    }

    @Override
    public RoomClass save(RoomClass roomClass) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, String.valueOf(roomClass.getComfortClass()));
            preparedStatement.setBigDecimal(2, roomClass.getPricePerDay());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                roomClass.setId(generatedKeys.getInt(ID));
            }
            return roomClass;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room class with id %s not found", roomClass.getId()), throwables.getCause());
        }
    }

    @Override
    public void update(RoomClass roomClass) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, String.valueOf(roomClass.getComfortClass()));
            preparedStatement.setInt(2, roomClass.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room class with id %s not found", roomClass.getId()), throwables.getCause());
        }
    }

    @Override
    public Optional<RoomClass> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room Class with id %s not found", id), throwables);
        }
    }

    public Optional<RoomClass> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            RoomClass roomClass = null;
            if (resultSet.next()) {
                roomClass = buildRoomClass(resultSet);
            }
            return Optional.ofNullable(roomClass);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room Class with id %s not found", id), throwables);
        }
    }

    public Optional<RoomClass> findByClass(String comfortClass) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_CLASS_SQL)) {
            preparedStatement.setString(1, comfortClass);
            var resultSet = preparedStatement.executeQuery();
            RoomClass roomClass = null;
            if (resultSet.next()) {
                roomClass = buildRoomClass(resultSet);
            }
            return Optional.ofNullable(roomClass);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room Class with comfort class %s not found", comfortClass), throwables);
        }
    }

    @Override
    public List<RoomClass> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<RoomClass> roomClasses = new ArrayList<>();
            while (resultSet.next()) {
                roomClasses.add(buildRoomClass(resultSet));
            }
            return roomClasses;
        } catch (SQLException throwables) {
            throw new DaoException("Room class not found", throwables);
        }
    }

    private RoomClass buildRoomClass(ResultSet resultSet) throws SQLException {
        return new RoomClass(
                resultSet.getInt(ID),
                RoomClassEnum.valueOf(resultSet.getObject(CLASS, String.class).toUpperCase()),
                resultSet.getBigDecimal(PRICE_PER_DAY)
        );
    }
}