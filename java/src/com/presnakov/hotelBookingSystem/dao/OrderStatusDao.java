package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.OrderStatus;
import com.presnakov.hotelBookingSystem.entity.OrderStatusEnum;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderStatusDao implements Dao<Integer, OrderStatus> {

    private static final OrderStatusDao INSTANCE = new OrderStatusDao();

    private static final String DELETE_SQL = """
            DELETE FROM order_status
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO order_status (order_status)
            VALUES (?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE order_status
            SET order_status = ?
            WHERE id = ?;
             """;
    private static final String FIND_ALL_SQL = """
            SELECT id,
            order_status
            FROM order_status
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String FIND_BY_STATUS_SQL = FIND_ALL_SQL + """
            WHERE order_status = ?
            """;
    private static final String ID = "id";
    private static final String ORDER_STATUS = "order_status";

    private OrderStatusDao() {
    }

    public static OrderStatusDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setInt(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Order status with id %s not found", id), throwables);
        }
    }

    @Override
    public OrderStatus save(OrderStatus orderStatus) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, String.valueOf(orderStatus.getOrderStatusEnum()));
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderStatus.setId(generatedKeys.getInt(ID));
            }
            return orderStatus;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Order status with id %s not found", orderStatus.getId()), throwables.getCause());
        }
    }

    @Override
    public void update(OrderStatus orderStatus) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, String.valueOf(orderStatus.getOrderStatusEnum()));
            preparedStatement.setInt(2, orderStatus.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Order status with id %s not found", orderStatus.getId()), throwables.getCause());
        }
    }

    @Override
    public Optional<OrderStatus> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Order Status with id %s not found", id), throwables);
        }
    }

    public Optional<OrderStatus> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            OrderStatus orderStatus = null;
            if (resultSet.next()) {
                orderStatus = buildOrderStatus(resultSet);
            }
            return Optional.ofNullable(orderStatus);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Order Status with id %s not found", id), throwables);
        }
    }

    public OrderStatus findByStatus(String status) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_STATUS_SQL)) {
            preparedStatement.setString(1, status);
            var resultSet = preparedStatement.executeQuery();
            OrderStatus orderStatus = null;
            if (resultSet.next()) {
                orderStatus = buildOrderStatus(resultSet);
            }
            return orderStatus;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Order Status with status %s not found", status), throwables);
        }
    }

    @Override
    public List<OrderStatus> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<OrderStatus> orderStatuses = new ArrayList<>();
            while (resultSet.next()) {
                orderStatuses.add(buildOrderStatus(resultSet));
            }
            return orderStatuses;
        } catch (SQLException throwables) {
            throw new DaoException("Order classes not found", throwables);
        }
    }

    private OrderStatus buildOrderStatus(ResultSet resultSet) throws SQLException {
        return new OrderStatus(
                resultSet.getInt(ID),
                OrderStatusEnum.valueOf(resultSet.getObject(ORDER_STATUS, String.class).toUpperCase())
        );
    }
}