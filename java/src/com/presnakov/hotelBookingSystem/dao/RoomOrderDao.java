package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.RoomOrder;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomOrderDao implements Dao<Integer, RoomOrder> {

    private static final RoomOrderDao INSTANCE = new RoomOrderDao();

    private static final String DELETE_SQL = """
            DELETE FROM room_order
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO room_order (user_id, room_id, order_status_id, payment_status_id, check_in_date, check_out_date)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE room_order
            SET user_id = ?,
                room_id = ?,
                order_status_id = ?,
                payment_status_id = ?,
                check_in_date = ?,
                check_out_date = ?
             WHERE id = ?;
             """;
    private static final String FIND_ALL_BY_ROOM_ID = """
            SELECT id,
            user_id,
            room_id,
            order_status_id,
            payment_status_id,
            check_in_date,
            check_out_date
            FROM room_order
            WHERE room_id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id,
            user_id,
            room_id,
            order_status_id,
            payment_status_id,
            check_in_date,
            check_out_date
            FROM room_order
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String FIND_BY_USER_ID_SQL = FIND_ALL_SQL + """
            WHERE user_id = ?
            AND order_status_id != ?;
            """;
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String ROOM_ID = "room_id";
    private static final String ORDER_STATUS_ID = "order_status_id";
    private static final String PAYMENT_STATUS_ID = "payment_status_id";
    private static final String CHECK_IN_DATE = "check_in_date";
    private static final String CHECK_OUT_DATE = "check_out_date";

    private RoomOrderDao() {
    }

    public static RoomOrderDao getInstance() {
        return INSTANCE;
    }

    private final UserDao userDao = UserDao.getInstance();
    private final RoomDao roomDao = RoomDao.getInstance();
    private final OrderStatusDao orderStatusDao = OrderStatusDao.getInstance();
    private final PaymentStatusDao paymentStatusDao = PaymentStatusDao.getInstance();

    public List<RoomOrder> findAllByRoomId(Integer roomId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_BY_ROOM_ID)) {
            preparedStatement.setObject(1, roomId);
            var resultSet = preparedStatement.executeQuery();
            List<RoomOrder> roomOrders = new ArrayList<>();
            while (resultSet.next()) {
                roomOrders.add(buildRoomOrder(resultSet));
            }
            return roomOrders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RoomOrder> findAllByUserId(Integer userId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_USER_ID_SQL)) {
            preparedStatement.setObject(1, userId);
            preparedStatement.setObject(2, 4);
            var resultSet = preparedStatement.executeQuery();
            List<RoomOrder> roomOrders = new ArrayList<>();
            while (resultSet.next()) {
                roomOrders.add(buildRoomOrder(resultSet));
            }
            return roomOrders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private RoomOrder buildRoomOrder(ResultSet resultSet) throws SQLException {
        return new RoomOrder(
                resultSet.getInt(ID),
                userDao.findById(resultSet.getInt(USER_ID),
                        resultSet.getStatement().getConnection()).orElse(null),
                roomDao.findById(resultSet.getInt(ROOM_ID),
                        resultSet.getStatement().getConnection()).orElse(null),
                orderStatusDao.findById(resultSet.getInt(ORDER_STATUS_ID),
                        resultSet.getStatement().getConnection()).orElse(null),
                paymentStatusDao.findById(resultSet.getInt(PAYMENT_STATUS_ID),
                        resultSet.getStatement().getConnection()).orElse(null),
                resultSet.getObject(CHECK_IN_DATE, LocalDate.class),
                resultSet.getObject(CHECK_OUT_DATE, LocalDate.class)
        );
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setInt(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room order with id %s not found", id), throwables);
        }
    }

    @Override
    public RoomOrder save(RoomOrder roomOrder) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, roomOrder.getUser().getId());
            preparedStatement.setInt(2, roomOrder.getRoom().getId());
            preparedStatement.setInt(3, roomOrder.getOrderStatus().getId());
            preparedStatement.setInt(4, roomOrder.getPaymentStatus().getId());
            preparedStatement.setObject(5, roomOrder.getCheckInDate());
            preparedStatement.setObject(6, roomOrder.getCheckOutDate());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                roomOrder.setId(generatedKeys.getInt(ID));
            }
            return roomOrder;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room order with id %s not found", roomOrder.getId()), throwables.getCause());
        }
    }

    @Override
    public void update(RoomOrder roomOrder) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, roomOrder.getUser().getId());
            preparedStatement.setInt(2, roomOrder.getRoom().getId());
            preparedStatement.setInt(3, roomOrder.getOrderStatus().getId());
            preparedStatement.setInt(4, roomOrder.getPaymentStatus().getId());
            preparedStatement.setObject(5, roomOrder.getCheckInDate());
            preparedStatement.setObject(6, roomOrder.getCheckOutDate());
            preparedStatement.setInt(7, roomOrder.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room order with id %s not found", roomOrder.getId()), throwables.getCause());
        }
    }

    @Override
    public Optional<RoomOrder> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room Class with id %s not found", id), throwables);
        }
    }

    public Optional<RoomOrder> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            RoomOrder roomOrder = null;
            if (resultSet.next()) {
                roomOrder = buildRoomOrder(resultSet);
            }
            return Optional.ofNullable(roomOrder);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Order with id %s not found", id), throwables);
        }
    }

    @Override
    public List<RoomOrder> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<RoomOrder> roomOrders = new ArrayList<>();
            while (resultSet.next()) {
                roomOrders.add(buildRoomOrder(resultSet));
            }
            return roomOrders;
        } catch (SQLException throwables) {
            throw new DaoException("Orders not found", throwables);
        }
    }
}