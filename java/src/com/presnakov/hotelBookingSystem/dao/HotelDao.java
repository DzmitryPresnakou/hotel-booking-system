package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.Hotel;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HotelDao implements Dao<Long, Hotel> {

    private static final HotelDao INSTANCE = new HotelDao();

    private static final String DELETE_SQL = """
            DELETE FROM hotel
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO hotel (name)
            VALUES (?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE hotel
            SET name = ?
             WHERE id = ?;
             """;
    private static final String FIND_ALL_SQL = """
            SELECT id,
            name
            FROM hotel
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String ID = "id";
    private static final String NAME = "name";

    private HotelDao() {
    }

    public static HotelDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Hotel with id %s not found", id), throwables);
        }
    }

    @Override
    public Hotel save(Hotel hotel) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, hotel.getName());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                hotel.setId(generatedKeys.getLong(ID));
            }
            return hotel;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Hotel with id %s not found", hotel.getId()), throwables.getCause());
        }
    }

    @Override
    public void update(Hotel hotel) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, hotel.getName());
            preparedStatement.setLong(2, hotel.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Hotel with id %s not found", hotel.getId()), throwables.getCause());
        }
    }

    @Override
    public Optional<Hotel> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room Class with id %s not found", id), throwables);
        }
    }

    public Optional<Hotel> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Hotel hotel = null;
            if (resultSet.next()) {
                hotel = buildHotel(resultSet);
            }
            return Optional.ofNullable(hotel);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Hotel with id %s not found", id), throwables);
        }
    }

    @Override
    public List<Hotel> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Hotel> hotels = new ArrayList<>();
            while (resultSet.next()) {
                hotels.add(buildHotel(resultSet));
            }
            return hotels;
        } catch (SQLException throwables) {
            throw new DaoException("Hotels not found", throwables);
        }
    }

    private Hotel buildHotel(ResultSet resultSet) throws SQLException {
        return new Hotel(
                resultSet.getLong(ID),
                resultSet.getString(NAME)
        );
    }
}